package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriteriaStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriteriaStatsResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.EvaluationPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.EvaluationStatus;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.NotFoundException;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.EvaluationMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService implements EvaluationPort {
    private final EvaluationRepository evaluationRepository;
    private final EnrollRepository enrollRepository;
    private final RubricRepository rubricRepository;
    private final EvaluationMapper evaluationMapper;

    //Rabbit communication
    private final RabbitService rabbitService;

    @Transactional
    public EvaluationResponseDTO saveEvaluation(EvaluationRequestDTO evaluationRequestDTO) {
        if (evaluationRequestDTO.getScore() == null || evaluationRequestDTO.getEvidenceUrl() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La evaluacion requiere una calificacion y evidencia");
        }
        if (evaluationRepository.existsByEnrollIdAndRubric_IdRubrica(
                evaluationRequestDTO.getEnroll(),
                evaluationRequestDTO.getRubric())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El estudiante ya ha sido evaluado en esta rubrica");
        }

        EnrollEntity enroll = enrollRepository.findById(evaluationRequestDTO.getEnroll())
                .orElseThrow(() -> new NotFoundException(
                        "Enroll no encontrado con id: " + evaluationRequestDTO.getEnroll()));

        RubricEntity rubric = rubricRepository.findByIdRubrica(evaluationRequestDTO.getRubric())
                .orElseThrow(() -> new NotFoundException(
                        "Rubric no encontrado con id: " + evaluationRequestDTO.getRubric()));

        Evaluation evaluation = EvaluationMapper.toModel(evaluationRequestDTO);

        // Sending message for Common_utilities_service
       // rabbitService.sendEvaluation(evaluationRequestDTO);

        EvaluationEntity evaluationEntity = EvaluationMapper.toEntity(evaluation);

        evaluationEntity.setEnroll(enroll);
        evaluationEntity.setRubric(rubric);
        evaluationEntity.setDescription(evaluationRequestDTO.getDescription());
        evaluationEntity.setEvaluationStatus(
                evaluationRequestDTO.getEvaluationStatus() != null
                        ? evaluationRequestDTO.getEvaluationStatus()
                        : EvaluationStatus.NO_EVALUADO
        );
        evaluationEntity.setScore(evaluationRequestDTO.getScore());
        evaluationEntity.setEvidenceUrl(evaluationRequestDTO.getEvidenceUrl());

        return EvaluationMapper.toDTO(
                EvaluationMapper.toModel(
                        evaluationRepository.saveAndFlush(evaluationEntity))
        );
    }

    @Override
    public Optional<EvaluationResponseDTO> getEvaluationsByEnrollAndRubric(Long enrollId, Long rubricId) {
        EvaluationEntity evaluationEntity = evaluationRepository.findByEnrollAndRubricId(enrollId, rubricId);
        return Optional.ofNullable(EvaluationMapper.toDTO(EvaluationMapper.toModel(evaluationEntity)));
    }

    @Override
    public List<CriteriaStatsResponseDTO> getCalificationsByCriteria(Long rubricaId, Long subjectId, String semester) {
        List<CriteriaStatsDTO> criteriaStatsDTOS = evaluationRepository.findCalificationAndLevel(rubricaId, subjectId, semester);

        if (criteriaStatsDTOS.isEmpty()) {
            return List.of();
        }

        // Agrupamos por ID de criterio y luego por nivel
        Map<Long, Map<String, Long>> grouped = criteriaStatsDTOS.stream()
                .collect(Collectors.groupingBy(
                        CriteriaStatsDTO::getIdCriterio,
                        Collectors.groupingBy(
                                CriteriaStatsDTO::getLevel,
                                Collectors.counting()
                        )
                ));

        // Mapeo auxiliar para obtener la descripción del criterio desde su ID
        Map<Long, String> idToDescription = criteriaStatsDTOS.stream()
                .collect(Collectors.toMap(
                        CriteriaStatsDTO::getIdCriterio,
                        CriteriaStatsDTO::getCrfDescripcion,
                        (v1, v2) -> v1 // en caso de conflicto, se queda con el primero
                ));

        // Crear la lista final
        List<CriteriaStatsResponseDTO> response = new ArrayList<>();

        grouped.forEach((idCriterio, levelsMap) -> {
            String descripcion = idToDescription.get(idCriterio);
            response.add(new CriteriaStatsResponseDTO(levelsMap, descripcion, idCriterio));
        });

        return response;
    }

    @Override
    public List<EvaluationResponseDTO> getEvaluations() {
        return evaluationRepository.findAll().stream()
               .map(EvaluationMapper::toModel)
                .map(EvaluationMapper::toDTO)
               .collect(Collectors.toList());
    }

    @Override
    public Optional<EvaluationResponseDTO> getEvaluationById(Long id) {
        EvaluationEntity evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Evaluation no encontrada con id: " + id));

        return Optional.of(
                EvaluationMapper.toDTO(
                        EvaluationMapper.toModel(evaluation))
        );
    }

    @Override
    public boolean deleteEvaluation(Long id) {
        Optional<EvaluationEntity> evaluationEntity = evaluationRepository.findById(id);
        if (evaluationEntity.isPresent()){
            rabbitService.sendDeleteEvaluation(evaluationEntity.get());
            evaluationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateEvaluation(Long id, EvaluationRequestDTO evaluationRequestDTO) {
        EvaluationEntity existingEvaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evaluation no encontrada con id: " + id));

        try {
            if (evaluationRequestDTO.getScore() == null || evaluationRequestDTO.getEvidenceUrl() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La evaluacion requiere una calificacion y evidencia");
            }
            if (!existingEvaluation.getEnroll().getId().equals(evaluationRequestDTO.getEnroll()) ||
                    !existingEvaluation.getRubric().getIdRubrica().equals(evaluationRequestDTO.getRubric())) {
                if (evaluationRepository.existsByEnrollIdAndRubric_IdRubrica(
                        evaluationRequestDTO.getEnroll(),
                        evaluationRequestDTO.getRubric())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Ya existe una evaluación para este estudiante y rúbrica");
                }
            }
            EnrollEntity enroll = enrollRepository.findById(evaluationRequestDTO.getEnroll())
                    .orElseThrow(() -> new NotFoundException(
                            "Enroll no encontrado con id: " + evaluationRequestDTO.getEnroll()));
            RubricEntity rubric = rubricRepository.findById(evaluationRequestDTO.getRubric())
                    .orElseThrow(() -> new NotFoundException(
                            "Rubric no encontrada con id: " + evaluationRequestDTO.getRubric()));

            existingEvaluation.setEnroll(enroll);
            existingEvaluation.setRubric(rubric);
            existingEvaluation.setDescription(evaluationRequestDTO.getDescription());
            existingEvaluation.setEvaluationStatus(
                    evaluationRequestDTO.getEvaluationStatus() != null
                            ? evaluationRequestDTO.getEvaluationStatus()
                            : EvaluationStatus.NO_EVALUADO
            );
            existingEvaluation.setScore(evaluationRequestDTO.getScore());
            existingEvaluation.setEvidenceUrl(evaluationRequestDTO.getEvidenceUrl());

            rabbitService.sendUpdateEvaluation(existingEvaluation);

            evaluationRepository.saveAndFlush(existingEvaluation);
            return true;
        } catch (NotFoundException | ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<EvaluationResponseDTO> getEvaluationsByEnrollId(Long enrollId) {
        return evaluationRepository.findByEnrollId(enrollId).stream()
                .map(EvaluationMapper::toModel)
                .map(EvaluationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EvaluationResponseDTO> getEvaluationsByRubricId(Long rubricId) {
        return evaluationRepository.findByRubric_IdRubrica(rubricId).stream()
                .map(EvaluationMapper::toModel)
                .map(EvaluationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BigDecimal> findEvaluationsByStudentAndSubject(Long studentId, Long subjectId, String semester, Long rubricId) {
        return evaluationRepository.findEvaluationsByStudentAndSubject(studentId, subjectId, semester, rubricId);
    }
}

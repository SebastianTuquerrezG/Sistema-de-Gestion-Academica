package unicauca.edu.co.sga.helper_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import unicauca.edu.co.sga.helper_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.helper_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.helper_service.application.ports.EvaluationPort;
import unicauca.edu.co.sga.helper_service.domain.enums.EvaluationStatus;
import unicauca.edu.co.sga.helper_service.domain.exceptions.NotFoundException;
import unicauca.edu.co.sga.helper_service.domain.models.Evaluation;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.mappers.EvaluationMapper;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.RubricRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService implements EvaluationPort {
    private final EvaluationRepository evaluationRepository;
    private final EnrollRepository enrollRepository;
    private final RubricRepository rubricRepository;

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
}

package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.enums.EvaluationStatus;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;

@Service
@Transactional
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final EnrollRepository enrollRepository;
    private final RubricRepository rubricRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository,
                             EnrollRepository enrollRepository,
                             RubricRepository rubricRepository) {
        this.evaluationRepository = evaluationRepository;
        this.enrollRepository = enrollRepository;
        this.rubricRepository = rubricRepository;
    }

    @Transactional
    public EvaluationResponseDTO createEvaluation(EvaluationRequestDTO evaluationRequestDTO) {

        if (evaluationRepository.existsByEnrollIdAndRubricId(
                evaluationRequestDTO.getEnroll(),
                evaluationRequestDTO.getRubric())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El estudiante ya ha sido evaluado en esta rubrica");
        }

        // VERIFICACIONES
        EnrollEntity enroll = enrollRepository.findById(evaluationRequestDTO.getEnroll())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Enroll no encontrado con id: " + evaluationRequestDTO.getEnroll()));

        RubricEntity rubric = rubricRepository.findById(evaluationRequestDTO.getRubric())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rubric no encontrado con id: " + evaluationRequestDTO.getRubric()));

        // CREAR NUEVA EVALUACIÓN
        EvaluationEntity evaluation = new EvaluationEntity();

        evaluation.setEnroll(enroll);
        evaluation.setRubric(rubric);
        evaluation.setDescription(evaluationRequestDTO.getDescription());
        evaluation.setEvaluationStatus(
                evaluationRequestDTO.getEvaluationStatus() != null
                        ? evaluationRequestDTO.getEvaluationStatus()
                        : EvaluationStatus.NO_EVALUADO
        );
        evaluation.setScore(evaluationRequestDTO.getScore());
        evaluation.setEvidenceUrl(evaluationRequestDTO.getEvidenceUrl());

        // Guardar y refrescar la entidad
        EvaluationEntity savedEvaluation = evaluationRepository.saveAndFlush(evaluation);

        // Convertir a DTO
        return EvaluationResponseDTO.builder()
                .id(savedEvaluation.getId())
                .enroll(savedEvaluation.getEnroll().getId())
                .rubric(savedEvaluation.getRubric().getId())
                .description(savedEvaluation.getDescription())
                .evaluationStatus(savedEvaluation.getEvaluationStatus())
                .score(savedEvaluation.getScore())
                .evidenceUrl(savedEvaluation.getEvidenceUrl())
                .created_at(savedEvaluation.getCreated_at())
                .updated_at(savedEvaluation.getUpdated_at())
                .build();
    }

    public EvaluationResponseDTO getEvaluationById(Long id) {
        EvaluationEntity evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Evaluation no encontrada con id: " + id));

        return EvaluationResponseDTO.builder()
                .id(evaluation.getId())
                .enroll(evaluation.getEnroll().getId())
                .rubric(evaluation.getRubric().getId())
                .description(evaluation.getDescription())
                .evaluationStatus(evaluation.getEvaluationStatus())
                .score(evaluation.getScore())
                .evidenceUrl(evaluation.getEvidenceUrl())
                .created_at(evaluation.getCreated_at())
                .updated_at(evaluation.getUpdated_at())
                .build();
    }
}

package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
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

    public EvaluationEntity createEvaluation(EvaluationRequestDTO evaluationRequestDTO) {
        // VERIFICACIONES
        EnrollEntity enroll = enrollRepository.findById(evaluationRequestDTO.getEnroll())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Enroll no encontrado con id: " + evaluationRequestDTO.getEnroll()));

        RubricEntity rubric = rubricRepository.findById(evaluationRequestDTO.getRubric())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rubric no encontrado con id: " + evaluationRequestDTO.getRubric()));

        // CREAR NUEVA EVALUACION
        EvaluationEntity evaluation = new EvaluationEntity();

        evaluation.setEnroll(enroll);
        evaluation.setRubric(rubric);
        evaluation.setDescription(evaluationRequestDTO.getDescription());
        evaluation.setEvaluationStatus(
                evaluationRequestDTO.getEvaluationStatus() != null
                        ? evaluationRequestDTO.getEvaluationStatus()
                        : EvaluationStatus.NO_EVALUADO
        );

        return evaluationRepository.save(evaluation);
    }

    public EvaluationEntity getEvaluationById(Long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Evaluation no encontrada con id: " + id));
    }
}

package unicauca.edu.co.sga.evaluation_service.application.services;

import org.springframework.ai.evaluation.EvaluationResponse;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;

import java.util.Optional;

@Service
//Este servicio se encargara de buscar lo necesario mediante sus ID
public class EvaluationService {
    /*private final EvaluationRepository evaluationRepository;
    private final EnrollRepository enrollRepository;
    private final RubricRepository rubricRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public Evaluation saveEvaluation(Evaluation evaluation) {

        EvaluationEntity entity = new EvaluationEntity();
        entity.setDescription(evaluation.getDescription());
        entity.setCreated_at(evaluation.getCreated_at());
        entity.setUpdated_at(evaluation.getUpdated_at());
        //entity.setEnroll(enroll);
        //entity.setRubric(rubric);

        EvaluationEntity savedEntity = evaluationRepository.save(entity);

        // Mapea la entidad guardada
        return new Evaluation(savedEntity.getId(), savedEntity.getDescription(), savedEntity.getCreated_at(), savedEntity.getUpdated_at());
    }*/
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public EvaluationEntity getEvaluationById(Long id) {
        return evaluationRepository.findById(id).orElse(null);
    }
    }

package unicauca.edu.co.sga.evaluation_service.infrastructure.adapters;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;

public class EvaluationAdapter {
    /*public static Evaluation toDomain(EvaluationRequestDTO requestDTO, EnrollEntity enroll, RubricEntity rubric) {

        return new Evaluation(null, requestDTO.getDescription(), null, null, enroll, rubric);
    }*/

    public static EvaluationEntity toEntity(Evaluation evaluation, EnrollEntity enroll, RubricEntity rubric) {
        return EvaluationEntity.builder()
                .id(evaluation.getId())
                .description(evaluation.getDescription())
                .enroll(enroll)
                .rubric(rubric)
                .created_at(evaluation.getCreated_at())
                .updated_at(evaluation.getUpdated_at())
                .build();
    }

    public static Evaluation toDomain(EvaluationEntity entity) {
        return Evaluation.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .build();
    }

    public static EvaluationResponseDTO toResponseDTO(Evaluation evaluation) {
        return EvaluationResponseDTO.builder()
                .id(evaluation.getId())
                .description(evaluation.getDescription())
                .created_at(evaluation.getCreated_at())
                .updated_at(evaluation.getUpdated_at())
                .build();
    }
}

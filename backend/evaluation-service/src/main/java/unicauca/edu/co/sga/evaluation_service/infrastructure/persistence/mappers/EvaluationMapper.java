package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;

@Component
public class EvaluationMapper {
    public static Evaluation toModel(EvaluationRequestDTO dto) {
        return Evaluation.builder()
                .enroll(dto.getEnroll())
                .rubric(dto.getRubric())
                .description(dto.getDescription())
                .evaluationStatus(dto.getEvaluationStatus())
                .score(dto.getScore())
                .evidenceUrl(dto.getEvidenceUrl())
                .build();
    }

    public static EvaluationResponseDTO toDTO(Evaluation domain) {
        return EvaluationResponseDTO.builder()
                .id(domain.getId())
                .enroll(domain.getEnroll())
                .rubric(domain.getRubric())
                .description(domain.getDescription())
                .created_at(domain.getCreated_at())
                .updated_at(domain.getUpdated_at())
                .evaluationStatus(domain.getEvaluationStatus())
                .score(domain.getScore())
                .evidenceUrl(domain.getEvidenceUrl())
                .build();
    }

    public static Evaluation toModel(EvaluationEntity entity) {
        return Evaluation.builder()
                .id(entity.getId())
                .enroll(entity.getEnroll().getId())
                .rubric(entity.getRubric().getId())
                .description(entity.getDescription())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .evaluationStatus(entity.getEvaluationStatus())
                .score(entity.getScore())
                .evidenceUrl(entity.getEvidenceUrl())
                .build();
    }

    public static EvaluationEntity toEntity(Evaluation domain) {
        return EvaluationEntity.builder()
                .id(domain.getId())
                .rubric(new RubricEntity(domain.getRubric(), null, null, null, null, null, null, null, null, null, null))
                .description(domain.getDescription())
                .created_at(domain.getCreated_at())
                .updated_at(domain.getUpdated_at())
                .evaluationStatus(domain.getEvaluationStatus())
                .score(domain.getScore())
                .evidenceUrl(domain.getEvidenceUrl())
                .build();
    }
}

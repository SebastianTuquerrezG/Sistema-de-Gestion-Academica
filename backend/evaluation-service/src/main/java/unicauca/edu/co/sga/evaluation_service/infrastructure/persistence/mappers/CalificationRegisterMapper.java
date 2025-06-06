package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CalificationRegisterRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CalificationRegisterResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.CalificationsRegister;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

@Component
public class CalificationRegisterMapper {
    public static CalificationsRegister toModel(CalificationRegisterRequestDTO dto){
        return CalificationsRegister.builder()
                .criteriaId(dto.getCriteriaId())
                .calification(dto.getCalification())
                .message(dto.getMessage())
                .level(dto.getLevel())
                .evaluation(dto.getEvaluationId())
                .build();
    }

    public static CalificationRegisterResponseDTO toDTO(CalificationsRegister domain){
        return CalificationRegisterResponseDTO.builder()
                .id(domain.getId())
                .criteriaId(domain.getCriteriaId())
                .calification(domain.getCalification())
                .message(domain.getMessage())
                .level(domain.getLevel())
                .evaluationId(domain.getEvaluation())
                .build();
    }

    public static CalificationsRegister toModel(CalificationRegisterEntity entity){
        return CalificationsRegister.builder()
                .id(entity.getId())
                .criteriaId(entity.getCriterio().getIdCriterio())
                .calification(entity.getCalification())
                .message(entity.getMessage())
                .level(entity.getLevel())
                .evaluation(entity.getEvaluation().getId())
                .build();
    }

    public static CalificationRegisterEntity toEntity(CalificationsRegister domain){
        EvaluationEntity evaluationEntity = new EvaluationEntity();
        evaluationEntity.setId(domain.getEvaluation());

        return CalificationRegisterEntity.builder()
                .id(domain.getId())
                .calification(domain.getCalification())
                .message(domain.getMessage())
                .level(domain.getLevel())
                .evaluation(evaluationEntity)
                .build();
    }
}

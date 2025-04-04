package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RubricRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Rubric;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;

@Component
public class RubricMapper {
    public static Rubric toModel(RubricRequestDTO dto){
        return Rubric.builder()
                .name(dto.getName())
                .study_objective(dto.getStudy_objective())
                .ra(dto.getRa_id())
                .competence(dto.getCompetence())
                .subject(null)
                .criteria(null)
                .build();
    }

    public static RubricResponseDTO toDTO(Rubric model){
        return RubricResponseDTO.builder()
                .id(model.getId())
                .ra_id(model.getRa())
                .build();
    }

    public static Rubric toModel(RubricEntity entity){
        return Rubric.builder()
                .id(entity.getId())
                .name(entity.getName())
                .study_objective(entity.getStudy_objective())
                .ra(entity.getRa().getId())
                .competence(entity.getCompetence())
                .subject(null)
                .evaluation(null)
                .criteria(null)
                .build();
    }

    public static RubricEntity toEntity(Rubric model){
        return RubricEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .status(model.getStatus())
                .competence(model.getCompetence())
                .subject(null)
                .criterias(null)
                .evaluation(null)
                .updated_at(null)
                .created_at(null)
                .build();
    }
}

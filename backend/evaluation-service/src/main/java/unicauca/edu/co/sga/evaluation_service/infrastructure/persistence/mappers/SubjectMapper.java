package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.SubjectRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.SubjectResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Subject;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.SubjectEntity;

@Component
public class SubjectMapper {
  public static Subject toModel(SubjectRequestDTO dto){
    return Subject.builder()
            .name(dto.getName())
            .credits(dto.getCredits())
            .status(dto.getStatus())
            .objectives(dto.getObjectives())
            .build();
  }

  public static SubjectResponseDTO toDTO(Subject domain){
    return SubjectResponseDTO.builder()
            .id(domain.getId())
            .credits(domain.getCredits())
            .status(domain.getStatus())
            .objective(domain.getObjectives())
            .name(domain.getName())
            .build();
  }

  public static Subject toModel(SubjectEntity entity){
    return Subject.builder()
            .id(entity.getId())
            .credits(entity.getCredits())
            .status(entity.getStatus())
            .objectives(entity.getObjectives())
            .build();
  }

  public static SubjectEntity toEntity(Subject domain){
    return SubjectEntity.builder()
            .id(domain.getId())
            .credits(domain.getCredits())
            .status(domain.getStatus())
            .objectives(domain.getObjectives())
            .created_at(null)
            .updated_at(null)
            .rubric(null)
            .build();
  }
}

package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RARequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RAResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.RA;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RAEntity;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class RAMapper {
    public static RA toModel(RAEntity entity) {
        if (entity == null) return null;

        return RA.builder()
                .id(entity.getId())
                .name(entity.getName())
                .course(entity.getCourse() != null
                        ? entity.getCourse().stream()
                        .map(CourseEntity::getId)
                        .collect(Collectors.toSet())
                        : new HashSet<>())
                .build();
    }

    public static RAEntity toEntity(RA model) {
        if (model == null) return null;

        // Solo se setean campos simples. Relaciones como `course` se deben manejar aparte.
        return RAEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public static RAResponseDTO toDTO(RA model) {
        if (model == null) return null;

        return RAResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public static RA toModel(RARequestDTO dto) {
        if (dto == null) return null;

        return RA.builder()
                .id(dto.getId())
                .build();
    }
}

package unicauca.edu.co.sga.helper_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.helper_service.application.dto.request.TeacherRequestDTO;
import unicauca.edu.co.sga.helper_service.application.dto.response.TeacherResponseDTO;
import unicauca.edu.co.sga.helper_service.domain.models.Teacher;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.TeacherEntity;

@Component
public class TeacherMapper {
    public static Teacher toModel(TeacherRequestDTO dto) {
        return Teacher.builder()
                .name(dto.getName())
                .identification(dto.getIdentification())
                .identificationType(dto.getIdentificationType())
                .degree(dto.getDegree())
                .teacherType(dto.getTeacherType())
                .status(dto.getStatus())
                .build();
    }

    public static TeacherResponseDTO toDTO(Teacher domain) {
        return TeacherResponseDTO.builder()
                .id(domain.getId())
                .name(domain.getName())
                .identification(domain.getIdentification())
                .identificationType(domain.getIdentificationType())
                .degree(domain.getDegree())
                .teacherType(domain.getTeacherType())
                .status(domain.getStatus())
                .build();
    }

    public static Teacher toModel(TeacherEntity entity) {
        return Teacher.builder()
                .id(entity.getId())
                .name(entity.getName())
                .identification(entity.getIdentification())
                .identificationType(entity.getIdentificationType())
                .degree(entity.getDegree())
                .teacherType(entity.getTeacherType())
                .status(entity.getStatus())
                .build();
    }

    public static TeacherEntity toEntity(Teacher domain) {
        return TeacherEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .identification(domain.getIdentification())
                .identificationType(domain.getIdentificationType())
                .degree(domain.getDegree())
                .teacherType(domain.getTeacherType())
                .status(domain.getStatus())
                .build();
    }
}

package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.StudentRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Student;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.StudentEntity;

import java.util.stream.Collectors;

@Component
public class StudentMapper {
    public static Student toModel(StudentRequestDTO dto){
        return Student.builder()
                .name(dto.getName())
                .identification(dto.getIdentification())
                .identification_type(dto.getType())
                .build();
    }

    public static StudentResponseDTO toDTO(Student domain){
        return StudentResponseDTO.builder()
                .id(domain.getId())
                .name(domain.getName())
                .identifier(domain.getIdentification())
                .type(domain.getIdentification_type())
                .build();
    }

    public static Student toModel(StudentEntity entity){
        return Student.builder()
                .id(entity.getId())
                .name(entity.getName())
                .identification(entity.getIdentification())
                .identification_type(entity.getIdentificationType())
                .build();
    }

    public static StudentEntity toEntity(Student domain){
        return StudentEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .identification(domain.getIdentification())
                .identificationType(domain.getIdentification_type())
                .build();
    }
}

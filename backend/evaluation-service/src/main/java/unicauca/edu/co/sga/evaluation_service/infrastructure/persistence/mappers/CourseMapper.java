package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CourseRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CourseResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Course;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.TeacherEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseMapper {
    public static Course toModel(CourseRequestDTO dto){
        return Course.builder()
                .teacher(Set.of(dto.getTeacher()))
                .subject(dto.getSubject())
                .ra(dto.getRa())
                .enroll(null)
                .build();
    }

    public static CourseResponseDTO toDTO(Course domain){
        return CourseResponseDTO.builder()
                .id(domain.getId())
                .subject(domain.getSubject())
                .teacher(domain.getTeacher())
                .ra(domain.getRa())
                .enroll(domain.getEnroll())
                .build();
    }

    public static Course toModel(CourseEntity entity){
        return Course.builder()
                .id(entity.getId())
                .subject(entity.getSubject().getId())
                .teacher(entity.getTeacher() != null ?
                        entity.getTeacher().stream()
                                .map(TeacherEntity::getId).collect(Collectors.toSet()) : null)
                .ra(entity.getRa().getId())
                .enroll(entity.getEnroll() != null ?
                        entity.getEnroll().stream()
                                .map(EnrollEntity::getId).collect(Collectors.toSet()) : null)
                .build();
    }

    // TODO: Create the logic for this method.
    public static CourseEntity toEntity(Course domain){
        return CourseEntity.builder()
                .id(domain.getId())
                .subject(null)
                .teacher(null)
                .enroll(null)
                .build();
    }
}

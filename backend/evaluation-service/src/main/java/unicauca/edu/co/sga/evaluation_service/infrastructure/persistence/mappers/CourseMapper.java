package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CourseRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CourseResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Course;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.*;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RARepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.SubjectRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.TeacherRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseMapper {
    private static SubjectRepository subjectRepository;
    private static TeacherRepository teacherRepository;
    private static RARepository raRepository;
    private static EnrollRepository enrollRepository;

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

    public static CourseEntity toEntity(Course domain){
        Optional<SubjectEntity> subject = subjectRepository.findById(domain.getId());
        Set<TeacherEntity> teachers = new HashSet<>(teacherRepository.findAllById(domain.getTeacher()));
        Optional<RAEntity> ra = raRepository.findById(domain.getRa());
        Set<EnrollEntity> enrollEntities = new HashSet<>(enrollRepository.findAllById(domain.getEnroll()));

        if (subject.isPresent() && ra.isPresent()){
            return CourseEntity.builder()
                    .id(domain.getId())
                    .subject(subject.get())
                    .teacher(teachers)
                    .ra(ra.get())
                    .enroll(enrollEntities) // Maybe this change in the future
                    .build();
        }
        return CourseEntity.builder()
                .id(domain.getId())
                .subject(null)
                .ra(null)
                .teacher(teachers)
                .enroll(enrollEntities)
                .build();
    }
}

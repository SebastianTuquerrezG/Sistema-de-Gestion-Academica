package unicauca.edu.co.sga.helper_service.infrastructure.persistence.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.helper_service.application.dto.request.CourseRequestDTO;
import unicauca.edu.co.sga.helper_service.application.dto.response.CourseResponseDTO;
import unicauca.edu.co.sga.helper_service.domain.models.Course;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.TeacherEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.RAEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.RARepository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.SubjectRepository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.TeacherRepository;

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

    @Autowired
    public CourseMapper(SubjectRepository subjectRepository,
                        TeacherRepository teacherRepository,
                        RARepository raRepository,
                        EnrollRepository enrollRepository) {
        CourseMapper.subjectRepository = subjectRepository;
        CourseMapper.teacherRepository = teacherRepository;
        CourseMapper.raRepository = raRepository;
        CourseMapper.enrollRepository = enrollRepository;
    }

    public static Course toModel(CourseRequestDTO dto){
        Set<Long> teacherIds = new HashSet<>();
        if (dto.getTeacher() != null) {
            teacherIds.add(dto.getTeacher());
        }
        return Course.builder()
                .teacher(teacherIds)
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
        if (domain.getSubject() == null || domain.getRa() == null) {
            throw new IllegalArgumentException("Ids must not be null");
        }
        Optional<SubjectEntity> subject = subjectRepository.findById(domain.getSubject());
        Set<TeacherEntity> teachers = new HashSet<>();
        if (domain.getTeacher() != null && !domain.getTeacher().isEmpty()) {
            teachers = new HashSet<>(teacherRepository.findAllById(domain.getTeacher()));
        }
        Optional<RAEntity> ra = raRepository.findById(domain.getRa());
        Set<EnrollEntity> enrollEntities = new HashSet<>();
        if (domain.getEnroll() != null && !domain.getEnroll().isEmpty()) {
            enrollEntities = new HashSet<>(enrollRepository.findAllById(domain.getEnroll()));
        }

        if (subject.isEmpty()) {
            throw new IllegalArgumentException("Subject with id " + domain.getSubject() + " not found");
        }

        if (ra.isEmpty()) {
            throw new IllegalArgumentException("RA with id " + domain.getRa() + " not found");
        }

        return CourseEntity.builder()
                .id(domain.getId())
                .subject(subject.get())
                .teacher(teachers)
                .ra(ra.get())
                .enroll(enrollEntities)
                .build();
    }
}

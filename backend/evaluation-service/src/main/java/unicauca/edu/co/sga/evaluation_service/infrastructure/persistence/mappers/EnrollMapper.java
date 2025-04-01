package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Enroll;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CourseRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.StudentRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EnrollMapper {
    private static StudentRepository studentRepository;
    private static CourseRepository courseRepository;

    @Autowired
    public EnrollMapper(StudentRepository studentRepository, CourseRepository courseRepository) {
        EnrollMapper.studentRepository = studentRepository;
        EnrollMapper.courseRepository = courseRepository;
    }

    public static Enroll toModel(EnrollRequestDTO dto){
        return Enroll.builder()
                .course(dto.getCourse())
                .student(dto.getStudent())
                .semester(dto.getSemester())
                .build();
    }

    public static EnrollResponseDTO toDTO(Enroll domain){
        return EnrollResponseDTO.builder()
                .id(domain.getId())
                .student(domain.getStudent())
                .course(domain.getCourse())
                .semester(domain.getSemester())
                .build();
    }

    public static Enroll toModel(EnrollEntity entity){
        return Enroll.builder()
                .id(entity.getId())
                .student(entity.getStudent().getId())
                .course(entity.getCourse().getId())
                .semester(entity.getSemester())
                .build();
    }

    public static EnrollEntity toEntity(Enroll domain){
        Optional<StudentEntity> student = studentRepository.findById(domain.getStudent());
        Optional<CourseEntity> course = courseRepository.findById(domain.getCourse());

        if (student.isPresent() && course.isPresent()){
            return EnrollEntity.builder()
                    .id(domain.getId())
                    .student(student.get())
                    .course(course.get())
                    .semester(domain.getSemester())
                    .build();
        }
        return EnrollEntity.builder()
                .id(domain.getId())
                .student(null)
                .course(null)
                .semester(domain.getSemester())
                .build();

    }
}

package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Enroll;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CourseRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.StudentRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EnrollMapper {

    // Note: Maybe this could be changed for another logic.
    private static StudentRepository studentRepository;
    private static CourseRepository courseRepository;
    private static EvaluationRepository evaluationRepository;

    public static Enroll toModel(EnrollRequestDTO dto){
        return Enroll.builder()
                .course(dto.getCourse())
                .student(dto.getStudent())
                .evaluation(Set.of(dto.getEvaluation()))
                .semester(dto.getSemester())
                .build();
    }

    public static EnrollResponseDTO toDTO(Enroll domain){
        return EnrollResponseDTO.builder()
                .id(domain.getId())
                .student(domain.getStudent())
                .course(domain.getCourse())
                .evaluation(domain.getEvaluation())
                .semester(domain.getSemester())
                .build();
    }

    public static Enroll toModel(EnrollEntity entity){
        return Enroll.builder()
                .id(entity.getId())
                .student(entity.getStudent().getId())
                .course(entity.getCourse().getId())
                .evaluation(entity.getEvaluation().stream()
                        .map(EvaluationEntity::getId).collect(Collectors.toSet()))
                .semester(entity.getSemester())
                .build();
    }

    public static EnrollEntity toEntity(Enroll domain){
        Optional<StudentEntity> student = studentRepository.findById(domain.getStudent());
        Optional<CourseEntity> course = courseRepository.findById(domain.getCourse());
        Set<EvaluationEntity> evaluations = new HashSet<>(evaluationRepository.findAllById(domain.getEvaluation()));

        if (student.isPresent() && course.isPresent()){
            return EnrollEntity.builder()
                    .id(domain.getId())
                    .student(student.get())
                    .course(course.get())
                    .evaluation(evaluations)
                    .semester(domain.getSemester())
                    .build();
        }
        return EnrollEntity.builder()
                .id(domain.getId())
                .student(null)
                .course(null)
                .evaluation(evaluations)
                .semester(domain.getSemester())
                .build();

    }
}

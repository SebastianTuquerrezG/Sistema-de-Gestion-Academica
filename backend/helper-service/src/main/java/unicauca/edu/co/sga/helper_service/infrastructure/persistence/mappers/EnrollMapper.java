package unicauca.edu.co.sga.helper_service.infrastructure.persistence.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.helper_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.helper_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.helper_service.domain.models.Enroll;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.CourseRepository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.StudentRepository;

import java.util.Optional;

@Component
public class EnrollMapper {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollMapper(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Enroll toModel(EnrollRequestDTO dto){
        return Enroll.builder()
                .course(dto.getCourse())
                .student(dto.getStudent())
                .semester(dto.getSemester())
                .build();
    }

    public EnrollResponseDTO toDTO(Enroll domain){
        return EnrollResponseDTO.builder()
                .id(domain.getId())
                .student(domain.getStudent())
                .course(domain.getCourse())
                .semester(domain.getSemester())
                .build();
    }

    public Enroll toModel(EnrollEntity entity){
        return Enroll.builder()
                .id(entity.getId())
                .student(entity.getStudent().getId())
                .course(entity.getCourse().getId())
                .semester(entity.getSemester())
                .build();
    }

    public EnrollEntity toEntity(Enroll domain){
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

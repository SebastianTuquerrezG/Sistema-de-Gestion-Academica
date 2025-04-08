package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CourseRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CourseResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.TeacherResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.CoursePort;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.NotFoundException;
import unicauca.edu.co.sga.evaluation_service.domain.models.Course;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.TeacherEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.CourseMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CourseRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService implements CoursePort {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public List<CourseResponseDTO> getCourses() {
        return courseRepository.findAll()
                .stream().map(CourseMapper::toModel)
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CourseResponseDTO> getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(CourseMapper::toModel)
                .map(CourseMapper::toDTO);
    }

    @Override
    public CourseResponseDTO saveCourse(CourseRequestDTO course) {
        Optional<TeacherEntity> teacher = teacherRepository.findById(course.getTeacher());
        if (teacher.isEmpty()) {
            throw new NotFoundException("Teacher " + course.getTeacher() +" not found");
        }
        Course courseModel = CourseMapper.toModel(course);
        CourseEntity courseEntity = CourseMapper.toEntity(courseModel);
        return CourseMapper.toDTO(CourseMapper.toModel(courseRepository.save(courseEntity)));
    }

    @Override
    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)){
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCourse(Long id, CourseRequestDTO course) {
        Optional<CourseEntity> entityExist = courseRepository.findById(id);
        if (entityExist.isPresent()){
            CourseEntity courseEntity = entityExist.orElseThrow(() -> new RuntimeException("Not found course Entity"));
            courseEntity.setTeacher(entityExist.get().getTeacher());
            courseEntity.setSubject(entityExist.get().getSubject());
            courseEntity.setRa(entityExist.get().getRa());
            courseEntity.setEnroll(entityExist.get().getEnroll());
            courseRepository.save(courseEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<CourseResponseDTO> getCoursesBySubjectId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la materia (subject_id) no puede ser null");
        }
        return courseRepository.findBySubjectId(id)
                .stream().map(CourseMapper::toModel)
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponseDTO> getCoursesByRAId(Long id) {
        return courseRepository.findByRaId(id)
                .stream().map(CourseMapper::toModel)
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponseDTO> getCoursesByTeacherId(Long id) {
        return courseRepository.findByTeacherId(id)
                .stream().map(CourseMapper::toModel)
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }
}

package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.EnrollPort;
import unicauca.edu.co.sga.evaluation_service.domain.models.Enroll;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.EnrollMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CourseRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollService implements EnrollPort {

    private final EnrollRepository enrollRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<EnrollResponseDTO> getEnrolls() {
        return enrollRepository.findAll().stream()
                .map(EnrollMapper::toModel)
                .map(EnrollMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EnrollResponseDTO> getEnrollsById(Long id) {
        return enrollRepository.findById(id)
                .map(EnrollMapper::toModel)
                .map(EnrollMapper::toDTO);
    }

    @Override
    public EnrollResponseDTO saveEnroll(EnrollRequestDTO enroll) {
        EnrollEntity enrollEntity = EnrollMapper.toEntity(EnrollMapper.toModel(enroll));
        return EnrollMapper.toDTO(EnrollMapper.toModel(enrollRepository.save(enrollEntity)));
    }

    @Override
    public boolean deleteEnroll(Long id) {
        if (enrollRepository.existsById(id)){
            enrollRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEnroll(Long id, EnrollRequestDTO enroll) {
        Optional<EnrollEntity> enrollExist = enrollRepository.findById(id);
        if (enrollExist.isPresent()){
            EnrollEntity enrollEntity = enrollExist.get();
            Optional<StudentEntity> studentEntity = studentRepository.findById(enroll.getStudent());
            Optional<CourseEntity> courseEntity = courseRepository.findById(enroll.getCourse());
            studentEntity.ifPresent(enrollEntity::setStudent);
            courseEntity.ifPresent(enrollEntity::setCourse);
            enrollEntity.setSemester(enroll.getSemester());
            enrollRepository.save(enrollEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<EnrollResponseDTO> getEnrollsByStudentId(Long studentId) {
        return enrollRepository.findByStudentId(studentId)
                .stream().map(EnrollMapper::toModel)
                .map(EnrollMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollResponseDTO> getEnrollsByCourseId(Long courseId) {
        return enrollRepository.findByCourseId(courseId)
                .stream().map(EnrollMapper::toModel)
                .map(EnrollMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollResponseDTO> getEnrollsBySemester(String semester) {
        return enrollRepository.findBySemester(semester)
                .stream().map(EnrollMapper::toModel)
                .map(EnrollMapper::toDTO)
                .collect(Collectors.toList());
    }
}

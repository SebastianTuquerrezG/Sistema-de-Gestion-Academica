package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.StudentRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.StudentPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.domain.models.Student;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.StudentMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentPort {
    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponseDTO> getStudents() {
        return studentRepository.findAll().stream()
                .map(StudentMapper::toModel)
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentResponseDTO> getStudentById(Long studentId) {
        return studentRepository.findByIdentification(studentId)
                .map(StudentMapper::toModel)
                .map(StudentMapper::toDTO);
    }

    @Override
    public StudentResponseDTO saveStudent(StudentRequestDTO studentDTO) {
        Student student = StudentMapper.toModel(studentDTO);
        StudentEntity studentEntity = StudentMapper.toEntity(student);
        return StudentMapper.toDTO(
                StudentMapper.toModel(
                        studentRepository.save(studentEntity)));
    }

    @Override
    public boolean deleteStudent(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateStudent(Long studentId, StudentRequestDTO student) {
        Optional<StudentEntity> studentExist = studentRepository.findByIdentification(studentId);
        if(studentExist.isPresent()){
            StudentEntity studentEntity = studentExist.orElseThrow(() -> new RuntimeException("Student not found"));
            studentEntity.setName(student.getName());
            studentEntity.setIdentification(student.getIdentification());
            studentEntity.setIdentification_type(student.getType());
            studentRepository.save(studentEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<StudentResponseDTO> getStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name).stream()
                .map(StudentMapper::toModel)
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentResponseDTO> getStudentsByIdentification(Long identification) {
        return studentRepository.findByIdentification(identification)
                .map(StudentMapper::toModel)
                .map(StudentMapper::toDTO);
    }

    @Override
    public List<StudentResponseDTO> getStudentsByIdentificationType(GeneralEnums.identificationType identificationType) {
        return studentRepository.findByIdentification_type(identificationType).stream()
                .map(StudentMapper::toModel)
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }
}

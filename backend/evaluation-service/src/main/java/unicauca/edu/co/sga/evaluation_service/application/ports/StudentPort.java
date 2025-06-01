package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.StudentRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import java.util.List;
import java.util.Optional;

public interface StudentPort {
    List<StudentResponseDTO> getStudents();

    Optional<StudentResponseDTO> getStudentById(Long studentId);

    StudentResponseDTO saveStudent(StudentRequestDTO student);

    boolean deleteStudent(Long studentId);

    boolean updateStudent(Long studentId, StudentRequestDTO student);

    List<StudentResponseDTO> getStudentsByName(String name);

    Optional<StudentResponseDTO> getStudentsByIdentification(Long identification);

    List<StudentResponseDTO> getStudentsByIdentificationType(GeneralEnums.identificationType identificationType);

    List<StudentResponseDTO> getStudentsByCourseAndPeriod(Long courseId, String period);

    Optional<Long> getStudentIdByName(String name);


}

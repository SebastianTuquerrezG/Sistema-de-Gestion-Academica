package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EnrollResponseDTO;

import java.util.List;
import java.util.Optional;

public interface EnrollPort {
    List<EnrollResponseDTO> getEnrolls();
    Optional<EnrollResponseDTO> getEnrollsById(Long id);
    EnrollResponseDTO saveEnroll(EnrollRequestDTO enroll);
    boolean deleteEnroll(Long id);
    boolean updateEnroll(Long id, EnrollRequestDTO enroll);
    List<EnrollResponseDTO> getEnrollsByStudentId(Long studentId);
    List<EnrollResponseDTO> getEnrollsByCourseId(Long courseId);
    List<EnrollResponseDTO> getEnrollsBySemester(String semester);
}

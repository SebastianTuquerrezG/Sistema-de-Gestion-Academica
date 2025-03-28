package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.TeacherRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.TeacherResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.domain.enums.TeacherEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.TeacherEntity;

import java.util.List;
import java.util.Optional;

public interface TeacherPort {
    List<TeacherResponseDTO> getTeachers();
    Optional<TeacherEntity> getTeacherById(Long id);
    TeacherResponseDTO saveTeacher(TeacherRequestDTO teacher);
    boolean deleteTeacher(Long id);
    boolean updateTeacher(Long id, TeacherRequestDTO teacher);
    Optional<TeacherResponseDTO> getTeacherByIdentification(Long identification);
    Optional<TeacherResponseDTO> getTeacherByName(String name);
    List<TeacherResponseDTO> getTeachersByDegree(String degree);
    List<TeacherResponseDTO> getTeachersByIdentificationType(GeneralEnums.identificationType identificationType);
    List<TeacherResponseDTO> getTeachersByStatus(GeneralEnums.status status);
    List<TeacherResponseDTO> getTeachersByTeacherType(TeacherEnums teacherType);
    List<TeacherResponseDTO> getTeachersByCourse(Long courseId);
}

package unicauca.edu.co.sga.common_utilities_service.application.ports;

import unicauca.edu.co.sga.common_utilities_service.application.dto.request.SubjectRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.SubjectResponseDTO;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;

import java.util.List;
import java.util.Optional;

public interface SubjectPort {
    List<SubjectResponseDTO> getSubjects();
    Optional<SubjectResponseDTO> getSubjectById(Long id);
    SubjectResponseDTO saveSubject(SubjectRequestDTO subject);
    boolean deleteSubject(Long id);
    boolean updateSubject(Long id, SubjectRequestDTO subject);
    Optional<SubjectResponseDTO> getByName(String name);
    List<SubjectResponseDTO> getByStatus(GeneralEnums.status status);
}


package unicauca.edu.co.sga.common_utilities_service.application.ports;

import unicauca.edu.co.sga.common_utilities_service.application.dto.request.RubricRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;

import java.util.List;
import java.util.Optional;

public interface RubricPort {
    List<RubricResponseDTO> getRubrics();
    Optional<RubricPort> getRubricById(Long id);
    RubricResponseDTO saveRubric(RubricRequestDTO rubric);
    boolean deleteRubric(Long id);
    boolean updateRubric(Long id, RubricRequestDTO rubric);
    Optional<RubricResponseDTO> getRubricByName(String name);
    List<RubricResponseDTO> getRubricsBySubject(Long id);
    List<RubricResponseDTO> getRubricsByRA(Long id);
    List<RubricResponseDTO> getRubricsByCriteria(Long id);
    List<RubricResponseDTO> getRubricsByStatus(GeneralEnums.status status);
}

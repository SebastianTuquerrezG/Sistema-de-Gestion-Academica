package unicauca.edu.co.sga.helper_service.application.ports;

import unicauca.edu.co.sga.helper_service.application.dto.response.CriteriaResponseDTO;
import unicauca.edu.co.sga.helper_service.application.dto.response.PerformanceLevelResponseDTO;
import unicauca.edu.co.sga.helper_service.application.dto.response.RubricResponseDTO;

import java.util.List;
import java.util.Optional;

public interface RubricIntegrationPort {
    List<RubricResponseDTO> getRubrics();
    List<CriteriaResponseDTO> getCriteria();
    List<PerformanceLevelResponseDTO> getPerformanceLevels();
    Optional<RubricResponseDTO> getRubricById(Long id);
    Optional<CriteriaResponseDTO> getCriteriaById(Long id);
    Optional<PerformanceLevelResponseDTO> getPerformanceLevelById(Long id);
    List<RubricResponseDTO> getRubricsByCriteria(Long id);
    List<CriteriaResponseDTO> getCriteriaByPerformanceLevel(Long id);
    Optional<PerformanceLevelResponseDTO> getPerformanceLevelsByName(String name);
}

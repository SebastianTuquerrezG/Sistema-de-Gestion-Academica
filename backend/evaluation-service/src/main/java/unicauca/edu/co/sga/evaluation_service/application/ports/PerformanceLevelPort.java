package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.PerformanceLevelRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.PerformanceLevelResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PerformanceLevelPort {
    List<PerformanceLevelResponseDTO> getPerformanceLevels();
    Optional<PerformanceLevelResponseDTO> getPerformanceLevelById(Long id);
    PerformanceLevelResponseDTO savePerformanceLevel(PerformanceLevelRequestDTO performanceLevel);
    boolean deletePerformanceLevel(Long id);
    boolean updatePerformanceLevel(Long id, PerformanceLevelRequestDTO performanceLevel);
    Optional<PerformanceLevelResponseDTO> getPerformanceLevelByName(String name);
}

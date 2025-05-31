package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.CriteriaRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CriteriaResponseDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;

import java.util.List;
import java.util.Optional;

public interface CriteriaPort {
    List<CriteriaResponseDTO> getCriteria();
    Optional<CriteriaResponseDTO> getCriteriaById(Long id);
    CriteriaEntity saveCriteria(CriteriaRequestDTO criteria);
    boolean deleteCriteria(Long id);
    boolean updateCriteria(Long id, CriteriaRequestDTO criteria);
    List<CriteriaResponseDTO> getCriteriaByPerformanceLevel(Long id);
}

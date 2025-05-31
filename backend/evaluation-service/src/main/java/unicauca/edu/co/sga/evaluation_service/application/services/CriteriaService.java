package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CriteriaRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CriteriaResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.CriteriaPort;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.CriteriaMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CriteriaRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CriteriaService implements CriteriaPort {

    private final CriteriaMapper criteriaMapper;
    private final CriteriaRepository criteriaRepository;

    @Override
    public List<CriteriaResponseDTO> getCriteria() {
        return List.of();
    }

    @Override
    public Optional<CriteriaResponseDTO> getCriteriaById(Long id) {
        return Optional.empty();
    }

    @Override
    public CriteriaEntity  saveCriteria(CriteriaRequestDTO criteria) {
        CriteriaEntity criteriaEntity = criteriaMapper.toEntity(criteria);
        criteriaRepository.save(criteriaEntity);
        return criteriaEntity;
    }

    @Override
    public boolean deleteCriteria(Long id) {
        Optional<CriteriaEntity> criteriaEntity = criteriaRepository.findById(id);
        if (criteriaEntity.isPresent()){
            criteriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCriteria(Long id, CriteriaRequestDTO criteria) {
        return false;
    }

    @Override
    public List<CriteriaResponseDTO> getCriteriaByPerformanceLevel(Long id) {
        return List.of();
    }
}

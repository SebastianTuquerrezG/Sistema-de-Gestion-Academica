package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.PerformanceLevelRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.PerformanceLevelResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.PerformanceLevelPort;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.PerformanceEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.PerformanceLevelMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.PerformanceRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerformanceLevelService implements PerformanceLevelPort {

    private final PerformanceRepository performanceRepository;
    private final PerformanceLevelMapper performanceLevelMapper;

    @Override
    public List<PerformanceLevelResponseDTO> getPerformanceLevels() {
        return List.of();
    }

    @Override
    public Optional<PerformanceLevelResponseDTO> getPerformanceLevelById(Long id) {
        return Optional.empty();
    }

    @Override
    public PerformanceEntity savePerformanceLevel(PerformanceLevelRequestDTO performanceLevel) {
        PerformanceEntity entity = performanceLevelMapper.toEntity(performanceLevel);
        performanceRepository.save(entity);
        return entity;
    }

    @Override
    public boolean deletePerformanceLevel(Long id) {
        Optional<PerformanceEntity> performanceEntity = performanceRepository.findById(id);
        if (performanceEntity.isPresent()){
            performanceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePerformanceLevel(Long id, PerformanceLevelRequestDTO performanceLevel) {
        return false;
    }

    @Override
    public Optional<PerformanceLevelResponseDTO> getPerformanceLevelByName(String name) {
        return Optional.empty();
    }
}

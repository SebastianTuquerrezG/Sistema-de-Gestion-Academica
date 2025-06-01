package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.PerformanceLevelRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.PerformanceLevelResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.PerformanceLevelPort;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.PerformanceEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.PerformanceLevelMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CriteriaRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.PerformanceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceLevelService implements PerformanceLevelPort {
    private final PerformanceRepository performanceRepository;
    private final PerformanceLevelMapper performanceLevelMapper;
    private final CriteriaRepository criteriaRepository;

    @Override
    public List<PerformanceLevelResponseDTO> getPerformanceLevels() {
        return performanceRepository.findAll().stream()
                .map(performanceLevelMapper::toModel)
                .map(performanceLevelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PerformanceLevelResponseDTO> getPerformanceLevelById(Long id) {
        return performanceRepository.findById(id)
                .map(performanceLevelMapper::toModel)
                .map(performanceLevelMapper::toDTO);
    }

    @Override
    public PerformanceLevelResponseDTO savePerformanceLevel(PerformanceLevelRequestDTO performanceLevel) {
        PerformanceEntity performanceEntity = performanceLevelMapper.toEntity(performanceLevelMapper.toModel(performanceLevel));
        return performanceLevelMapper.toDTO(performanceLevelMapper.toModel(performanceRepository.save(performanceEntity)));
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
        Optional<PerformanceEntity> performanceEntity = performanceRepository.findById(id);
        if (performanceEntity.isPresent()){
            PerformanceEntity performance = performanceEntity.get();
            Optional<CriteriaEntity> criteriaEntity = criteriaRepository.findById(performanceLevel.getIdCriterio());
            performance.setCriterio(criteriaEntity.orElse(null));
            performance.setRangoNota(performanceLevel.getRangoNota());
            performance.setNivelDescripcion(performanceLevel.getNivelDescripcion());

            performanceRepository.save(performance);
            return true;
        }
        return false;
    }

    @Override
    public Optional<PerformanceLevelResponseDTO> getPerformanceLevelByName(String name) {
//        return performanceLevelMapper.toDTO(performanceLevelMapper.toModel(performanceRepository.findById()))
        return Optional.empty();
    }
}

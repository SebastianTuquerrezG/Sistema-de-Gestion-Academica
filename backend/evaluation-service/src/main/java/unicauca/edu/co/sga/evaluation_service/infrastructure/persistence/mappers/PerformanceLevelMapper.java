package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.PerformanceLevelRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.PerformanceLevelResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.PerformanceLevel;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.PerformanceEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CriteriaRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PerformanceLevelMapper {
    private final CriteriaRepository criteriaRepository;

    public PerformanceLevel toModel(PerformanceLevelRequestDTO dto){
        return PerformanceLevel.builder()
                .description(dto.getNivelDescripcion())
                .idCriterio(dto.getIdCriterio())
                .rangoNota(dto.getRangoNota())
                .build();
    }

    public PerformanceLevelResponseDTO toDTO(PerformanceLevel domain){
        return PerformanceLevelResponseDTO.builder()
                .id(domain.getId())
                .description(domain.getDescription())
                .idCriterio(domain.getIdCriterio())
                .range(domain.getRangoNota())
                .build();
    }

    public PerformanceLevel toModel(PerformanceEntity entity){
        return PerformanceLevel.builder()
                .id(entity.getIdNivel())
                .description(entity.getNivelDescripcion())
                .idCriterio(entity.getCriterio().getIdCriterio())
                .rangoNota(entity.getRangoNota())
                .build();
    }

    public PerformanceEntity toEntity(PerformanceLevel dto){
        Optional<CriteriaEntity> criteriaEntity = criteriaRepository.findById(dto.getIdCriterio());
        return PerformanceEntity.builder()
                .idNivel(dto.getId())
                .criterio(criteriaEntity.orElse(null))
                .nivelDescripcion(dto.getDescription())
                .rangoNota(dto.getRangoNota())
                .build();
    }
}

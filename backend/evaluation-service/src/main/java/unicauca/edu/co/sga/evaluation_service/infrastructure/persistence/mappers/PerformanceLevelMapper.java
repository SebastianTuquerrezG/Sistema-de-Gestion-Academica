package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.PerformanceLevelRequestDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.PerformanceEntity;

@Component
@RequiredArgsConstructor
public class PerformanceLevelMapper {
    public PerformanceEntity toEntity(PerformanceLevelRequestDTO dto){
        return PerformanceEntity.builder()
                .idNivel(dto.getIdNivel())
                .criterio(dto.getCriterio())
                .rangoNota(dto.getRangoNota())
                .nivelDescripcion(dto.getNivelDescripcion())
                .build();
    }
}

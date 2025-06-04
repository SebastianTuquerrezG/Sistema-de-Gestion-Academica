package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CriteriaRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CriteriaResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Criteria;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.PerformanceEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.PerformanceRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CriteriaMapper {

    private final RubricRepository rubricRepository;
    private final PerformanceRepository performanceRepository;

    public static Criteria toModel(CriteriaRequestDTO dto) {
        return Criteria.builder()
                .id(dto.getIdRubrica())
                .comment(dto.getCrfComentario())
                .description(dto.getCrfDescripcion())
                .percentage(dto.getCrfPorcentaje())
                .score(dto.getCrfNota())
                .performance_level(dto.getNiveles() != null
                        ? dto.getNiveles().stream()
                        .map(PerformanceEntity::getIdNivel) // Asumiendo que PerformanceEntity tiene getId()
                        .collect(Collectors.toSet())
                        : null)
                .rubricId(dto.getIdRubrica() != null ? dto.getIdRubrica() : null)
                .build();
    }

    public CriteriaResponseDTO toDTO(Criteria entity) {
        return CriteriaResponseDTO.builder()
                .id(entity.getId())
                .comment(entity.getComment())
                .description(entity.getDescription())
                .percentage(entity.getPercentage())
                .score(entity.getScore())
                .performanceLevel(entity.getPerformance_level().stream()
                        .map(id -> performanceRepository.findById(id).orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()))
                .rubricId(entity.getRubricId() != null ? entity.getRubricId() : null)
                .build();
    }

    public static Criteria toModel(CriteriaEntity entity) {
        return Criteria.builder()
                .id(entity.getIdCriterio())
                .comment(entity.getCrfComentario())
                .description(entity.getCrfDescripcion())
                .percentage(entity.getCrfPorcentaje())
                .score(entity.getCrfNota())
                .performance_level(entity.getNiveles() != null
                        ? entity.getNiveles().stream()
                        .map(PerformanceEntity::getIdNivel) // Asumiendo que PerformanceEntity tiene getId()
                        .collect(Collectors.toSet())
                        : null)
                .rubricId(entity.getRubric() != null ? entity.getRubric().getIdRubrica() : null)
                .build();
    }

    public CriteriaEntity toEntity(Criteria dto){
        return CriteriaEntity.builder()
                .idCriterio(dto.getId())
                .crfComentario(dto.getComment())
                .crfDescripcion(dto.getDescription())
                .crfPorcentaje(dto.getPercentage())
                .crfNota(dto.getScore())
                .rubric(rubricRepository.findById(dto.getRubricId()).orElse(null))
                .niveles(dto.getPerformance_level() != null
                        ? dto.getPerformance_level().stream()
                        .map(performanceRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }
}

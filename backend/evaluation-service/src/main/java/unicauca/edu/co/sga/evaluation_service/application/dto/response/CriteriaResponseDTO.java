package unicauca.edu.co.sga.evaluation_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.PerformanceEntity;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriteriaResponseDTO {
    private Long id;
    private String comment;
    private String description;
    private Integer percentage;
    private Float score;
    private Long rubricId;
    private Set<PerformanceEntity> performanceLevel;
}

package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CriterionHistogramDTO {
    private Long criterionId;
    private String criterionName;
    private Map<String, Integer> levelCounts;
}
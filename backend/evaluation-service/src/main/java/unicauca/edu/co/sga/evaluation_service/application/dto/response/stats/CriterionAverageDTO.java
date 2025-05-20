package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CriterionAverageDTO {
    private Long criterionId;
    private String criterionName;
    private Double averageScore;
    private String comment;
}

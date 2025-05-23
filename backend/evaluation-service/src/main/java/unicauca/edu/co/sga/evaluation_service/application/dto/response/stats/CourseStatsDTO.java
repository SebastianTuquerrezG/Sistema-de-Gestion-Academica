package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseStatsDTO {
    private String raName;

    private BigDecimal average;
    private BigDecimal median;
    private BigDecimal standardDeviation;
    private BigDecimal minScore;
    private BigDecimal maxScore;
    private int studentsCount;
}
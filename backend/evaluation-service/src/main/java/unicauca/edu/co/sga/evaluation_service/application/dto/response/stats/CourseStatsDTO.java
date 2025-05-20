package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CourseStatsDTO {
    private Long courseId;
    private String subjectName;
    private String semester;
    private BigDecimal average;
    private BigDecimal median;
    private BigDecimal standardDeviation;
    private BigDecimal minScore;
    private BigDecimal maxScore;
    private int studentsCount;
}
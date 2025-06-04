package unicauca.edu.co.sga.evaluation_service.domain.models.stats;

import java.util.List;

public class RubricStats {
    private String rubricId;
    private List<CriterionStat> criteriaStats;
    private double average;
    private double median;
}

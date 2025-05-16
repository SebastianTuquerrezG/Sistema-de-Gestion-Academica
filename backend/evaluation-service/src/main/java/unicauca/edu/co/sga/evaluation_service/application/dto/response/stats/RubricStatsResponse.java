package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.Data;

@Data
public class RubricStatsResponse {
    private double averageScore;
    private double minScore;
    private double maxScore;

    public RubricStatsResponse(double averageScore, double minScore, double maxScore) {
        this.averageScore = averageScore;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }
}

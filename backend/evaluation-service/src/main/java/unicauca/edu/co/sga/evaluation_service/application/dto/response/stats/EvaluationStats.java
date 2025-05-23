package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import java.math.BigDecimal;

public class EvaluationStats {
    private BigDecimal score;
    private String raName;

    public EvaluationStats(BigDecimal score, String raName) {
        this.score = score;
        this.raName = raName;
    }

    public BigDecimal getScore() {
        return score;
    }

    public String getRaName() {
        return raName;
    }
}

package unicauca.edu.co.sga.evaluation_service.infrastructure.utils;

import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.RubricStatsResponse;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

import java.math.BigDecimal;
import java.util.List;

public class StatsCalculator {

    public static BigDecimal calculateAverage(List<BigDecimal> scores) {
        if (scores == null || scores.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = scores.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(new BigDecimal(scores.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calculateMedian(List<BigDecimal> scores) {
        if (scores.isEmpty()) return BigDecimal.ZERO;

        List<BigDecimal> sorted = scores.stream().sorted().toList();
        int size = sorted.size();

        if (size % 2 == 0) {
            BigDecimal sum = sorted.get(size/2).add(sorted.get(size/2 - 1));

            return sum.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
        }
        return sorted.get(size/2);
    }

    public static BigDecimal calculateStandardDeviation(List<BigDecimal> scores, BigDecimal mean) {
        if (scores.isEmpty()) return BigDecimal.ZERO;

        BigDecimal variance = scores.stream()
                .map(score -> score.subtract(mean).pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(scores.size()), 10, BigDecimal.ROUND_HALF_UP);

        double stdDev = Math.sqrt(variance.doubleValue());
        return new BigDecimal(stdDev).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
package unicauca.edu.co.sga.evaluation_service.infrastructure.utils;

import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.RubricStatsResponse;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class StatsCalculator {

    // ESTADISTICAS BASICAZZZ
    public static RubricStatsResponse calculate(List<EvaluationEntity> evaluations) {
        if (evaluations == null || evaluations.isEmpty()) {
            return new RubricStatsResponse(0.0, 0.0, 0.0);
        }

        // SCORES
        List<BigDecimal> scores = evaluations.stream()
                .map(EvaluationEntity::getScore)
                .filter(score -> score != null) // Filtra scores nulos
                .collect(Collectors.toList());

        // METRICAS
        double average = calculateAverage(scores);
        double min = calculateMin(scores);
        double max = calculateMax(scores);

        // RESULTADOS
        return new RubricStatsResponse(average, min, max);
    }

    private static double calculateAverage(List<BigDecimal> scores) {
        return scores.stream()
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0.0);
    }

    private static double calculateMin(List<BigDecimal> scores) {
        return scores.stream()
                .mapToDouble(BigDecimal::doubleValue)
                .min()
                .orElse(0.0);
    }

    private static double calculateMax(List<BigDecimal> scores) {
        return scores.stream()
                .mapToDouble(BigDecimal::doubleValue)
                .max()
                .orElse(0.0);
    }
}
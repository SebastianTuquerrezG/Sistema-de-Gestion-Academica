package unicauca.edu.co.sga.evaluation_service.application.services.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.FilterStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.StatsPort;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.EvaluationStats;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.stats.StatsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService implements StatsPort{
    private final EvaluationRepository evaluationRepository;
    private final StatsRepository statsRepository;

    @Override
    public CourseStatsDTO getStatsByRubric(FilterStatsDTO filter) {

        List<EvaluationStats> evaluations = statsRepository.findStatsByFilters(
                filter.getRubricName(),
                filter.getSubjectName(),
                filter.getPeriod()
        );

        if (evaluations.isEmpty()) {
            return new CourseStatsDTO();
        }

        List<Double> values = evaluations.stream()
                .map(e -> e.score().doubleValue())
                .sorted()
                .toList();

        double average = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double median = calculateMedian(values);
        double stdDev = calculateStdDev(values, average);
        double min = values.get(0);
        double max = values.get(values.size() - 1);

        String raName = evaluations.get(0).raName();

        CourseStatsDTO dto = new CourseStatsDTO();
        dto.setRaName(raName);
        dto.setAverage(BigDecimal.valueOf(roundTo2Decimals(average)));
        dto.setMedian(BigDecimal.valueOf(roundTo2Decimals(median)));
        dto.setStandardDeviation(BigDecimal.valueOf(roundTo2Decimals(stdDev)));
        dto.setMinScore(BigDecimal.valueOf(roundTo2Decimals(min)));
        dto.setMaxScore(BigDecimal.valueOf(roundTo2Decimals(max)));
        dto.setStudentsCount(values.size());

        return dto;
    }

    private double roundTo2Decimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private double calculateMedian(List<Double> values) {
        int n = values.size();
        return n % 2 == 0
                ? (values.get(n / 2 - 1) + values.get(n / 2)) / 2.0
                : values.get(n / 2);
    }

    private double calculateMode(List<Double> values) {
        Map<Double, Long> freqMap = values.stream()
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()));
        return freqMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0.0);
    }

    private double calculateStdDev(List<Double> values, double mean) {
        double variance = values.stream()
                .mapToDouble(v -> Math.pow(v - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }
}

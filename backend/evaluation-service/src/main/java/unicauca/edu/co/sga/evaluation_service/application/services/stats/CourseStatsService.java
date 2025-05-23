package unicauca.edu.co.sga.evaluation_service.application.services.stats;

import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.FilterStatsDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.SubjectRepository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.EvaluationStats;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.stats.StatsRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.utils.StatsCalculator;

import java.util.List;
import java.math.BigDecimal;

@Service
public class CourseStatsService {

    private final EvaluationRepository evaluationRepo;
    private final SubjectRepository subjectRepo;
    private final StatsRepository statsRepository;

    public CourseStatsService(EvaluationRepository evaluationRepo, SubjectRepository subjectRepo, StatsRepository statsRepository) {
        this.evaluationRepo = evaluationRepo;
        this.subjectRepo = subjectRepo;
        this.statsRepository = statsRepository;
    }

    public CourseStatsDTO calculateCourseStats(FilterStatsDTO filter) {
        List<EvaluationStats> stats = statsRepository.findStatsByFilters(
                filter.getRubricName(),
                filter.getSubjectName(),
                filter.getPeriod()
        );

        if (stats == null || stats.isEmpty()) {
            throw new IllegalArgumentException("No hay evaluaciones para los filtros seleccionados");
        }

        List<BigDecimal> scores = stats.stream().map(EvaluationStats::getScore).toList();

        BigDecimal average = StatsCalculator.calculateAverage(scores);
        BigDecimal median = StatsCalculator.calculateMedian(scores);
        BigDecimal stdDev = StatsCalculator.calculateStandardDeviation(scores, average);
        BigDecimal min = scores.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal max = scores.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        String raName = stats.get(0).getRaName();

        return CourseStatsDTO.builder()
                .raName(raName)
                .average(average)
                .median(median)
                .standardDeviation(stdDev)
                .minScore(min)
                .maxScore(max)
                .studentsCount(scores.size())
                .build();
    }

}

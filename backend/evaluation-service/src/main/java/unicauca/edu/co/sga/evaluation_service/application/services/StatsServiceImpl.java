package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.application.ports.StatsService;
import unicauca.edu.co.sga.evaluation_service.domain.models.stats.RubricStats;
import unicauca.edu.co.sga.evaluation_service.domain.models.stats.StudentProgress;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.StatsRepository;

import java.util.List;

@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepo;

    @Override
    public RubricStats calculateRubricStats(String rubricId) {
        List<Double> scores = statsRepo.findScoresByRubricId(rubricId);
        //double mean = StatisticsCalculator.calculateMean(scores);
        return null; //new RubricStats(rubricId, mean, ...);
    }

    @Override
    public StudentProgress getStudentProgress(String studentId) {
        return null;
    }
}
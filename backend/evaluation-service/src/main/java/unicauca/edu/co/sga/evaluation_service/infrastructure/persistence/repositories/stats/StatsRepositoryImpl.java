package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.stats;

import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.EvaluationStats;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.List;

@Repository
public class StatsRepositoryImpl implements StatsRepository {
    private final EvaluationRepository evaluationRepository;

    public StatsRepositoryImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public List<EvaluationStats> findStatsByFilters(String rubricName, String subjectName, String period) {
        return evaluationRepository.findStatsByFilters(rubricName, subjectName, period);
    }
}

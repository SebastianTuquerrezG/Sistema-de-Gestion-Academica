package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.stats;

import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.EvaluationStats;

import java.util.List;

public interface StatsRepository {
    List<EvaluationStats> findStatsByFilters(String rubricName, String subjectName, String period);
}

package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.stats;

import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriteriaAverageDTO;

import java.util.List;

public interface CriteriaStatsRepository {
    List<CriteriaAverageDTO> findCriteriaAverages(String subjectName, String rubricName, String period);
}


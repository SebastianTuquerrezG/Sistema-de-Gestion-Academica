package unicauca.edu.co.sga.evaluation_service.application.services.stats;

import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriteriaAverageDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.List;

@Service
public class CriteriaStatsService {

    private final EvaluationRepository evaluationRepository;

    public CriteriaStatsService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public List<CriteriaAverageDTO> getCriteriaAverages(String subjectName, String rubricName, String period) {
        return evaluationRepository.findCriteriaAverages(subjectName, rubricName, period);
    }
}


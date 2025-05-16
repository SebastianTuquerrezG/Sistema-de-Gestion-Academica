package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.RubricStatsResponse;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.utils.StatsCalculator;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsTestController {

    private final EvaluationRepository evaluationRepository;

    public StatsTestController(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @GetMapping("/rubric/{rubricId}")
    public RubricStatsResponse getStatsByRubric(@PathVariable Long rubricId) {

        List<EvaluationEntity> evaluations = evaluationRepository.findByRubric_IdRubrica(rubricId);

        return StatsCalculator.calculate(evaluations);
    }
}

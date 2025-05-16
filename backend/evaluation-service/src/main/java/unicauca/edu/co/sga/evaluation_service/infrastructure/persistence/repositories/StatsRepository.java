package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;

import java.util.List;

public interface StatsRepository {
    List<Double> findScoresByRubricId(String rubricId);
    List<Evaluation> findEvaluationsByStudentId(String studentId);
}

package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriterionAverageDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CriterionStatsService {
    private final EvaluationRepository evaluationRepository;

    public List<CriterionAverageDTO> calculateAveragesByRubric(Long rubricId) {

        List<EvaluationEntity> evaluations = evaluationRepository.findByRubric_IdRubrica(rubricId);
        if (evaluations.isEmpty()) {
            return Collections.emptyList();
        }

        //CRITERIOS RUBRICA
        RubricEntity rubric = evaluations.getFirst().getRubric();
        List<CriteriaEntity> criteriaList = rubric.getCriterios()
                .stream()
                .sorted(Comparator.comparing(CriteriaEntity::getIdCriterio))
                .toList();

        // VALIDACION
        evaluations.forEach(evaluation -> {
            if (evaluation.getCalifications().size() != criteriaList.size()) {
                throw new IllegalStateException(
                        "La evaluación " + evaluation.getId() + " no coincide con los criterios de la rubrica"
                );
            }
        });

        // CALCULO PROMEDIO
        List<CriterionAverageDTO> result = new ArrayList<>();
        for (int i = 0; i < criteriaList.size(); i++) {
            final int criterionIndex = i;
            CriteriaEntity criterion = criteriaList.get(i);

            List<Double> scores = evaluations.stream()
                    .map(eval -> eval.getCalifications().get(criterionIndex).getCalification())
                    .toList();

            double average = scores.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            result.add(new CriterionAverageDTO(
                    criterion.getIdCriterio(),
                    criterion.getCrfDescripcion(),
                    average,
                    criterion.getCrfComentario()
            ));
        }

        return result;
    }
}

package unicauca.edu.co.sga.evaluation_service.application.services.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.FilterStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramByCriteriaDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramRowDTO;
import unicauca.edu.co.sga.evaluation_service.domain.enums.CalificationEnums;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistogramCriteriaService {

    private final EvaluationRepository evaluationRepository;

    public List<HistogramByCriteriaDTO> getHistogramByCriteria(String subjectName, String rubricName, String period) {
        return evaluationRepository.findHistogramByCriteria(subjectName, rubricName, period);
    }

}

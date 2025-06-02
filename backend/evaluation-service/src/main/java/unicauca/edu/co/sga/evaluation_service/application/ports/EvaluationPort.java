package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriteriaStatsResponseDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EvaluationPort {
    List<EvaluationResponseDTO> getEvaluations();
    Optional<EvaluationResponseDTO> getEvaluationById(Long id);
    EvaluationResponseDTO saveEvaluation(EvaluationRequestDTO evaluation);
    boolean deleteEvaluation(Long id);
    boolean updateEvaluation(Long id, EvaluationRequestDTO evaluation);
    List<EvaluationResponseDTO> getEvaluationsByEnrollId(Long enrollId);
    List<EvaluationResponseDTO> getEvaluationsByRubricId(Long rubricId);
    Optional<BigDecimal> findEvaluationsByStudentAndSubject(Long studentId, Long subjectId, String semester, Long rubricId);
    Optional<EvaluationResponseDTO> getEvaluationsByEnrollAndRubric(Long enrollId, Long rubricId);
    List<CriteriaStatsResponseDTO> getCalificationsByCriteria(Long rubricaId, Long subjectId, String semester);
}

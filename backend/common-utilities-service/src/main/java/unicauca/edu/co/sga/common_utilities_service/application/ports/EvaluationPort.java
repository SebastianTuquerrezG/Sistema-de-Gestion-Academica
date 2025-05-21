package unicauca.edu.co.sga.common_utilities_service.application.ports;

import unicauca.edu.co.sga.common_utilities_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.EvaluationResponseDTO;

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
}

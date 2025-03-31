package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.EvaluationStatus;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationRequestDTO {
    private Long enroll;
    private Long rubric;
    private String description;
    private EvaluationStatus evaluationStatus = EvaluationStatus.NO_EVALUADO;
    private BigDecimal score;
    private String evidenceUrl;
}
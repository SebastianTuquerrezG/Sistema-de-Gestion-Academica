package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "La calificacion es requerida")
    private BigDecimal score;

    @NotBlank(message = "Se requiere una evidencia!")
    private String evidenceUrl;
}
package unicauca.edu.co.sga.evaluation_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.EvaluationStatus;

import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationResponseDTO {
    private Long id;
    private Long enroll;
    private Long rubric;
    private String description;
    private Date created_at;
    private Date updated_at;
    private EvaluationStatus evaluationStatus;
    private BigDecimal score;
    private String evidenceUrl;

    public EvaluationResponseDTO(Long id, Long enroll, Long rubric, String description) {
        this.id = id;
        this.enroll = enroll;
        this.rubric = rubric;
        this.description = description;
    }
}

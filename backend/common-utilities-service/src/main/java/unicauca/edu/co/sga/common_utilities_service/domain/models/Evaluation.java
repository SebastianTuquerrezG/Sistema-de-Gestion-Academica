package unicauca.edu.co.sga.common_utilities_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.EvaluationStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Evaluation {
    private Long id;
    private Long enroll;
    private Long rubric;
    private String description;
    private EvaluationStatus evaluationStatus;
    private BigDecimal score;
    private String evidenceUrl;
    private Set<Long> calification;
    private Date created_at;
    private Date updated_at;
}

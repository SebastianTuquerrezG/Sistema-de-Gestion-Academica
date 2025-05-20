package unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationResponseViewDTO {

    private String description;
    private List<CriteriaResponseViewDTO> criterias;
    private List<CalificationResponseViewDTO> califications;
    private BigDecimal totalScore;

}

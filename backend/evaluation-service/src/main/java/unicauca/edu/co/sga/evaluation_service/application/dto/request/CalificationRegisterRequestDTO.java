package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalificationRegisterRequestDTO {
    private Double calification;
    private Long criteriaId;
    private Long evaluationId;
    private String level;
    private String message;


}

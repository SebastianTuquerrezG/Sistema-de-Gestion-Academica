package unicauca.edu.co.sga.common_utilities_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalificationRegisterResponseDTO {
    private Long id;
    private Double calification;
    private String message;
    private String level;
    private Long evaluationId;
}

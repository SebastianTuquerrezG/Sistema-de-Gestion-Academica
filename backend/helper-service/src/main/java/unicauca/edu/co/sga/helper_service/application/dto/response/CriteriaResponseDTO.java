package unicauca.edu.co.sga.helper_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriteriaResponseDTO {
    private Long id;
    private String description;
    private Long score;
}

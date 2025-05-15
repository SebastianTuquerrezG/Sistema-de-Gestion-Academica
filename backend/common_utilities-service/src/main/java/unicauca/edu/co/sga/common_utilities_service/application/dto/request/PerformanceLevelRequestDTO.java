package unicauca.edu.co.sga.common_utilities_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceLevelRequestDTO {
    private String name;
    private String description;
    private Double range;
}

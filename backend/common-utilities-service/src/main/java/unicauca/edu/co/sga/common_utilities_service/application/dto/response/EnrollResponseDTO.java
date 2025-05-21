package unicauca.edu.co.sga.common_utilities_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollResponseDTO {
    private Long id;
    private Long course;
    private Long student;
    private String semester;
}

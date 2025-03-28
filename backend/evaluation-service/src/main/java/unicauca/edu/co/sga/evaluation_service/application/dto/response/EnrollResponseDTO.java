package unicauca.edu.co.sga.evaluation_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollResponseDTO {
    private Long id;
    private Long course;
    private Long student;
    private Set<Long> evaluation;
    private String semester;
}

package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.*;
@Data
@Getter
@Setter
@NoArgsConstructor
public class CriteriaDTO {
    private Long rubricId;
    private Long subjectId;
    private String semester;
}
package unicauca.edu.co.sga.evaluation_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubricResponseDTO {
    private Long id;
    private String name;
    private String studyObjective;
    private int rubricScore;
    private Long ra_id;
    private String raName;
    private GeneralEnums.status status;
}

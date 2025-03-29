package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectRequestDTO {
    private String name;
    private Integer credits;
    private String objectives;
    private GeneralEnums.status status;
}

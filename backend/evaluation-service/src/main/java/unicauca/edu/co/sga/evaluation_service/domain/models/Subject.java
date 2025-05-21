package unicauca.edu.co.sga.evaluation_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {
    private Long id;
    private String name;
    private Integer credits;
    private String objectives;
    private GeneralEnums.status status;
}

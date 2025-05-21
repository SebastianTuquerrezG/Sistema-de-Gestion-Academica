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
public class Student {
    private Long id;
    private String name;
    private Long identification;
    private GeneralEnums.identificationType identification_type;
}

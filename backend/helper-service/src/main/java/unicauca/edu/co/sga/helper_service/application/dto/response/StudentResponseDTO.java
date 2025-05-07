package unicauca.edu.co.sga.helper_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.helper_service.domain.enums.GeneralEnums;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDTO {
    private Long id;
    private String name;
    private Long identifier;
    private GeneralEnums.identificationType type;
}

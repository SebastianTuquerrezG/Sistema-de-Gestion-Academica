package unicauca.edu.co.sga.helper_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.helper_service.domain.enums.GeneralEnums;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResponseDTO {
    private Long id;
    private String name;
    private Integer credits;
    private String objective;
    private GeneralEnums.status status;
}

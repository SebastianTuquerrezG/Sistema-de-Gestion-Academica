package unicauca.edu.co.sga.helper_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.helper_service.domain.enums.GeneralEnums;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubricRequestDTO {
    private Long idRubrica;
    private String nombreRubrica;
    private String objetivoEstudio;
    private int notaRubrica;
    private Long ra_id;
    private GeneralEnums.status status;
}

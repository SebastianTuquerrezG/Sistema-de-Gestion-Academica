package unicauca.edu.co.sga.common_utilities_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rubric {
    private Long idRubrica;
    private String nombreRubrica;
    private String objetivoEstudio;
    private int notaRubrica;
    private Set<Long> subject;
    private Long ra;
    private Set<Long> criterios;
    private GeneralEnums.status status;
    private Set<Long> evaluation;
}

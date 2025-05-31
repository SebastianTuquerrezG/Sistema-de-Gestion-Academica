package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubricRequestDTO {
    private Long idRubrica;
    private Long idMateria;
    private String nombreRubrica;
    private String nombreMateria;
    private int notaRubrica;
    private String objetivoEstudio;
    List<CriteriaEntity> criterios;
    private GeneralEnums.status estado;
    private Long raId;
}

package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceLevelRequestDTO {
    //    private Long idNivel;
    private Long idCriterio;
    //    private CriteriaEntity criterio;
    private String nivelDescripcion;
    private String rangoNota;
}

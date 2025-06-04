package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.PerformanceEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriteriaRequestDTO {
    private Long idRubrica;
    private String crfDescripcion;
    private Integer crfPorcentaje;
    private Float crfNota;
    private String crfComentario;
    private List<PerformanceEntity> niveles;
}

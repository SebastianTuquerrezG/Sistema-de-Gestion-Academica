package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
public class CriteriaAverageDTO {
    private Long idCriterio;
    private String criterioDescripcion;
    private Double promedioNota;

    public CriteriaAverageDTO(Long idCriterio, String criterioDescripcion, Double promedioNota) {
        this.idCriterio = idCriterio;
        this.criterioDescripcion = criterioDescripcion;
        this.promedioNota = promedioNota != null ? Math.round(promedioNota * 100.0) / 100.0 : null;
    }
}


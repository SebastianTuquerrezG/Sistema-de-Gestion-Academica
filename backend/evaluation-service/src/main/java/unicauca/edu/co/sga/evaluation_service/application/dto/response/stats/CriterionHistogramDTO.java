package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//BORRAR
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriterionHistogramDTO {
    private Long idCriterio;
    private String descripcion;
    private List<NivelDesempenoDTO> niveles;

    public CriterionHistogramDTO(Long idCriterio, String descripcion) {
        this.idCriterio = idCriterio;
        this.descripcion = descripcion;
        this.niveles = new ArrayList<>();
    }

    public List<NivelDesempenoDTO> getNiveles() {
        return niveles;
    }

    public void setNiveles(List<NivelDesempenoDTO> niveles) {
        this.niveles = niveles;
    }

}


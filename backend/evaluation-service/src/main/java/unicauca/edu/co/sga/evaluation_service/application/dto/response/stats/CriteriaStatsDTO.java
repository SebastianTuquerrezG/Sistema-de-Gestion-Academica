package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CriteriaStatsDTO {
    private String level;
    private Double calification;
    private String rangoNota;
    private String crfDescripcion;
    private Long idCriterio;

    public CriteriaStatsDTO(String level, Double calification, String rangoNota, String crfDescripcion, Long idCriterio) {
        this.level = level;
        this.calification = calification;
        this.rangoNota = rangoNota;
        this.crfDescripcion = crfDescripcion;
        this.idCriterio = idCriterio;
    }
}
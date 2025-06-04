package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;


import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CriteriaStatsResponseDTO {
    private Map<String, Long> levels;
    private String crfDescripcion;
    private Long idCriterio;

    public CriteriaStatsResponseDTO(Map<String, Long> levels, String crfDescripcion, Long idCriterio) {
        this.levels = levels;
        this.crfDescripcion = crfDescripcion;
        this.idCriterio = idCriterio;
    }
}
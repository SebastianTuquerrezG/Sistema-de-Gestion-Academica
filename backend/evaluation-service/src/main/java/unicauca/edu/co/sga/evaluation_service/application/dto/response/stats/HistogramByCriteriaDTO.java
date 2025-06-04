package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unicauca.edu.co.sga.evaluation_service.domain.enums.CalificationEnums;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class HistogramByCriteriaDTO {
    private Long criteriaId;
    private String criteriaDescription;
    private Long countNivel1; // 0-2.9
    private Long countNivel2; // 3.0-4.5
    private Long countNivel3; // 4.6-5.0

    public HistogramByCriteriaDTO(Long criteriaId, String criteriaDescription, Long countNivel1, Long countNivel2, Long countNivel3) {
        this.criteriaId = criteriaId;
        this.criteriaDescription = criteriaDescription;
        this.countNivel1 = countNivel1;
        this.countNivel2 = countNivel2;
        this.countNivel3 = countNivel3;
    }
}

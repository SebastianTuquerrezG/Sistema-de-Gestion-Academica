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
    private String criteriaName;
    private Map<CalificationEnums, Long> countByLevel;

    public HistogramByCriteriaDTO(String criteriaName, Map<CalificationEnums, Long> countByLevel) {
        this.criteriaName = criteriaName;
        this.countByLevel = countByLevel;
    }
}

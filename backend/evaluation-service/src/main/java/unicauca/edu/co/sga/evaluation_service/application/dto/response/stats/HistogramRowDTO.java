package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistogramRowDTO {
    private String criteriaName;
    private String level;
    private Long count;

    public HistogramRowDTO(String criteriaName, String level, Long count) {
        this.criteriaName = criteriaName;
        this.level = level;
        this.count = count;
    }
}

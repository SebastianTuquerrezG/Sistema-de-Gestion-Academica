package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterStatsDTO {
    private String rubricName;
    private String subjectName;
    private String period;
}

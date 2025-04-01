package unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceLevelResponseViewDTO {

    String name;
    private String description;
    //Lo trabajan con puntos, no con nota
    private String range;

}

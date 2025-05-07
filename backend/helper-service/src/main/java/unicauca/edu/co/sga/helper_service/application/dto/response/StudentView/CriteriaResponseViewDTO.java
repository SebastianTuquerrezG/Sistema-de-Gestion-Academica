package unicauca.edu.co.sga.helper_service.application.dto.response.StudentView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.helper_service.application.dto.response.StudentView.PerformanceLevelResponseViewDTO;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriteriaResponseViewDTO {

    private String descriptionCriteria;
    private List<PerformanceLevelResponseViewDTO> levels;
    //Les falta esto
    private float percentage;

}

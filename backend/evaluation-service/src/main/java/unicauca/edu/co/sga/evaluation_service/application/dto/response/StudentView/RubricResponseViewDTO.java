package unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubricResponseViewDTO {
    private Long idRubric;
    private String nameRubric;
   // private Date created_at;

}

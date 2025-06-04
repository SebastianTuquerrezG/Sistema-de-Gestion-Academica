package unicauca.edu.co.sga.evaluation_service.domain.exceptions.RubricEvaluation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ErrorExtended {

    private String code;
    private String message;

}

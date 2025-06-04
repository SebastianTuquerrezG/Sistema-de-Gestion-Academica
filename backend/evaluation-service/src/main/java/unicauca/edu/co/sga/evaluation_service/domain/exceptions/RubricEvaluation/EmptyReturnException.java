package unicauca.edu.co.sga.evaluation_service.domain.exceptions.RubricEvaluation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmptyReturnException extends RuntimeException {

    private final String errorCode;

    public EmptyReturnException(String errorCode, String message)  {
        super(message);
        this.errorCode = errorCode;
    }

}

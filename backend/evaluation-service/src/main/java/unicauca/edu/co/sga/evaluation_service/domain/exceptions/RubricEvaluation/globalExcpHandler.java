package unicauca.edu.co.sga.evaluation_service.domain.exceptions.RubricEvaluation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class globalExcpHandler {

    @ExceptionHandler(EmptyReturnException.class)
    public ResponseEntity<ErrorExtended> handleCanNotBeEmpty(EmptyReturnException ex) {
        ErrorExtended error = new ErrorExtended(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Manejo genérico para otras excepciones
    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<ErrorExtended> handleGlobalException(DefaultException ex) {
        ErrorExtended error = new ErrorExtended("0", "Error interno no identificado");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

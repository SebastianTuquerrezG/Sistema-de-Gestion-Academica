package unicauca.edu.co.sga.evaluation_service.domain.exceptions;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message) {
        super(message);
    }
}

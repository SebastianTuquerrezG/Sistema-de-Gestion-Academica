package unicauca.edu.co.sga.common_utilities_service.domain.exceptions;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message) {
        super(message);
    }
}

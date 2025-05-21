package unicauca.edu.co.sga.evaluation_service.domain.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}

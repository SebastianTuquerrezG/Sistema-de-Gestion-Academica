package unicauca.edu.co.sga.evaluation_service.domain.exceptions.subject;

public class SubjectAlreadyExistException extends RuntimeException {
    public SubjectAlreadyExistException(String message) {
        super(message);
    }
}

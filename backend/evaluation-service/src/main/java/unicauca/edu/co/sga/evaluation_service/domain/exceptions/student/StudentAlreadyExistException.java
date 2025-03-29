package unicauca.edu.co.sga.evaluation_service.domain.exceptions.student;

public class StudentAlreadyExistException extends RuntimeException{
    public StudentAlreadyExistException(String message) {
        super(message);
    }
}

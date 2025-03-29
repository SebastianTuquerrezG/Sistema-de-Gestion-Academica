package unicauca.edu.co.sga.evaluation_service.domain.exceptions.student;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message) {
        super(message);
    }
}

package unicauca.edu.co.sga.evaluation_service.domain.exceptions.teacher;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(String message) {
        super(message);
    }
}

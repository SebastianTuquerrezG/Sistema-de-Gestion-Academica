package unicauca.edu.co.sga.evaluation_service.domain.exceptions.teacher;

public class TeacherAlreadyExistException extends RuntimeException {
    public TeacherAlreadyExistException(String message) {
        super(message);
    }
}

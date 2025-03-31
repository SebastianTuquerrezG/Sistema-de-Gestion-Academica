package unicauca.edu.co.sga.evaluation_service.domain.exceptions.course;

public class CourseAlreadyExistException extends RuntimeException {
    public CourseAlreadyExistException(String message) {
        super(message);
    }
}

package unicauca.edu.co.sga.evaluation_service.domain.exceptions.course;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}

package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.CourseResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.EvaluationResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.RubricResponseViewDTO;

import java.util.List;

public interface RubricEvaluationPort {

    public List<CourseResponseViewDTO> getCoursesFromStudentPeriod(Long idStudent, String semester);
    public List<String> getPeriods(Long idStudent);
    public List<RubricResponseViewDTO> getRubricsFromStudentCoursePeriod(Long idStudent, Long idSubject, String semester);
    public List<EvaluationResponseViewDTO> getRubricsFromStudentCoursePeriodRubric(Long idStudent, Long idSubject, String semester, Long idRubric);
}

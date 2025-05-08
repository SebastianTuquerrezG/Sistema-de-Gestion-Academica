package unicauca.edu.co.sga.helper_service.application.ports;

import unicauca.edu.co.sga.helper_service.application.dto.response.StudentView.EvaluationResponseViewDTO;
import unicauca.edu.co.sga.helper_service.application.dto.response.StudentView.RubricResponseViewDTO;
import unicauca.edu.co.sga.helper_service.application.dto.response.StudentView.SubjectResponseViewDTO;

import java.util.List;

public interface RubricEvaluationPort {

    public List<SubjectResponseViewDTO> getSubjectsFromStudentPeriod(Long idStudent, String semester);
    public List<String> getPeriods(Long idStudent);
    public List<RubricResponseViewDTO> getRubricsFromStudentSubjectPeriod(Long idStudent, Long idSubject, String semester);
    public EvaluationResponseViewDTO getRubricsFromStudentSubjectPeriodRubric(Long idStudent, Long idSubject, String semester, Long idRubric);
}

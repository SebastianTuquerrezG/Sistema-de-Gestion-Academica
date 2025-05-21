package unicauca.edu.co.sga.common_utilities_service.application.ports;

import unicauca.edu.co.sga.common_utilities_service.application.dto.response.StudentView.EvaluationResponseViewDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.StudentView.RubricResponseViewDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.StudentView.SubjectResponseViewDTO;

import java.util.List;

public interface RubricEvaluationPort {

    List<SubjectResponseViewDTO> getSubjectsFromStudentPeriod(Long idStudent, String semester);
    List<String> getPeriods(Long idStudent);
    List<RubricResponseViewDTO> getRubricsFromStudentSubjectPeriod(Long idStudent, Long idSubject, String semester);
    EvaluationResponseViewDTO getRubricsFromStudentSubjectPeriodRubric(Long idStudent, Long idSubject, String semester, Long idRubric);
}

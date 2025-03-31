package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.SubjectResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.EvaluationResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.RubricResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RubricEvaluationPort;

import java.util.List;

@RestController
@RequestMapping("/RubricEvaluation")
public class RubricEvaluationController {


    private final RubricEvaluationPort rubricEvaluationPort;

    public RubricEvaluationController(RubricEvaluationPort rubricEvaluationPort) {
        this.rubricEvaluationPort = rubricEvaluationPort;
    }


    //Recuperar cursos de un estudiante en un periodo especifico (de la tabla enroll hay que traer el nombre del curso y el nombre del profesor que dicta el curso)
    @GetMapping("/{idStudent}/{semester}")
    public ResponseEntity<List<SubjectResponseViewDTO>> getSubjectsFromStudentPeriod(@PathVariable Long idStudent, @PathVariable String semester) {
        return ResponseEntity.ok(rubricEvaluationPort.getSubjectsFromStudentPeriod(idStudent, semester));
    }


    //Recuperar periodos (cuando se unda a otros periodos se tienen que mostrar las fechas de los periodos)
    @GetMapping("/{idStudent}")
    public ResponseEntity<List<String>> getPeriods(@PathVariable Long idStudent) {
        System.out.println("EntraPeriods");
        return ResponseEntity.ok(rubricEvaluationPort.getPeriods(idStudent));
    }


    //Recuperar rubricas de curso especifico, estudiante especifico y periodo especifico (Nombre rubrica y fecha de creacion)
    @GetMapping("/{idStudent}/{idSubject}/{semester}")
    public ResponseEntity<List<RubricResponseViewDTO>> getRubricsFromStudentCoursePeriod(@PathVariable Long idStudent, @PathVariable Long idSubject, @PathVariable String semester) {
        return ResponseEntity.ok(rubricEvaluationPort.getRubricsFromStudentSubjectPeriod(idStudent, idSubject, semester));
    }


    //Recuperar información de evaluación como descripción rubrica, criterios - niveles - evaluación
    @GetMapping("/{idStudent}/{idSubject}/{semester}/{idRubric}")
    public ResponseEntity<List<EvaluationResponseViewDTO>> getRubricEvaluation(@PathVariable Long idStudent, @PathVariable Long idSubject, @PathVariable String semester, @PathVariable Long idRubric) {
        return ResponseEntity.ok(rubricEvaluationPort.getRubricsFromStudentSubjectPeriodRubric(idStudent, idSubject, semester, idRubric));
    }

}

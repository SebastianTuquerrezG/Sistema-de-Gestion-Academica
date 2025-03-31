package unicauca.edu.co.sga.evaluation_service.application.services;

import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.CourseResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.EvaluationResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.RubricResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RubricEvaluationPort;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EnrollRepository;

import java.util.List;

@Service
public class RubricEvaluationService implements RubricEvaluationPort {


    private final EnrollRepository enrollRepository;

    public RubricEvaluationService(EnrollRepository enrollRepository) {
        this.enrollRepository = enrollRepository;
    }

    @Override
    public List<CourseResponseViewDTO> getCoursesFromStudentPeriod(Long idStudent, String semester) {
        return enrollRepository.findCoursesAndTeachersByStudentAndSemester(idStudent, semester);
    }

    @Override
    public List<String> getPeriods(Long idStudent) {
        return List.of();
    }

    @Override
    public List<RubricResponseViewDTO> getRubricsFromStudentCoursePeriod(Long idStudent, Long idSubject, String semester) {
        return enrollRepository.findRubricNamesAndDates(idStudent, idSubject, semester);
    }

    @Override
    public List<EvaluationResponseViewDTO> getRubricsFromStudentCoursePeriodRubric(Long idStudent, Long idSubject, String semester, Long idRubric) {
        /*List<EvaluationEntity> evaluations = evaluationRepository.findEvaluationsByStudentId(studentId);

        if (evaluations.isEmpty()) {
            return null; // O lanzar una excepción
        }

        // Convertir EvaluationEntity a DTO Calification
        List<Calification> califications = evaluations.stream()
                .map(e -> new Calification(e.getDescription(), e.getCreatedAt()))
                .collect(Collectors.toList());

        // Suponiendo que cada evaluación tiene criterios asociados
        List<Criteria> criterias = evaluations.stream()
                .flatMap(e -> e.getCriteriaList().stream())  // Extraer los criterios
                .map(c -> new Criteria(c.getName(), c.getWeight())) // Convertir a DTO
                .collect(Collectors.toList());

        return RubricEvaluation.builder()
                .calification(evaluations.get(0).getScore()) // Usar la calificación del primer registro
                .name(evaluations.get(0).getEnroll().getStudent().getName())
                .studyObjective("Some objective") // Esto depende de tu lógica
                .califications(califications)
                .criterias(criterias)
                .build();*/

        return null;

    }


}

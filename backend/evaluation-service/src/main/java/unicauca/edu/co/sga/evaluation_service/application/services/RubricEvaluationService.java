package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.*;
import unicauca.edu.co.sga.evaluation_service.application.ports.RubricEvaluationPort;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.RubricEvaluation.EmptyReturnException;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RubricEvaluationService implements RubricEvaluationPort {

    private final EnrollRepository enrollRepository;
    private final CriteriaRepository criteriaRepository;
    private final CalificationRegisterRepository calificationRegisterRepository;
    private final RubricRepository rubricRepository;
    private final EvaluationRepository evaluationRepository;

    @Override
    public List<SubjectResponseViewDTO> getSubjectsFromStudentPeriod(Long idStudent, String semester) {
        System.out.println("entraconsultarmaterias");
        return enrollRepository.findSubjectsAndTeachersByStudentAndSemester(idStudent, semester);
    }

    @Override
    public SubjectHeaderResponseViewDTO getSubjectFromStudentPeriod(Long idStudent, String semester, Long idSubject) {
        System.out.println("entraconsultarmaterias");
        return enrollRepository.findSubjectAndTeachersByStudentAndSemester(idStudent, semester, idSubject);

    }

    @Override
    public List<String> getPeriods(Long idStudent) {
        return enrollRepository.findDistinctSemestersByStudentId(idStudent);
    }

    @Override
    public List<RubricResponseViewDTO> getRubricsFromStudentSubjectPeriod(Long idStudent, Long idSubject, String semester) {
       return enrollRepository.findRubricNamesAndDates(idStudent, idSubject, semester);
    }

    @Override
    public EvaluationResponseViewDTO getRubricsFromStudentSubjectPeriodRubric(Long idStudent, Long idSubject, String semester, Long idRubric) {

        String rubricDescription = String.
                valueOf(rubricRepository.findRubricDescriptionByStudent(idRubric,idSubject,idStudent,semester).
                        orElseThrow(() -> new EmptyReturnException("1","No existe la rubrica"))); //Es el caso de que a la hora de crear la rubrica la descripcion es obligatoria

        List<CriteriaEntity> criterias = criteriaRepository
                .findByRubricAndStudentAndSubject(idRubric,idStudent,idSubject,semester);


        if (criterias.isEmpty()) {
            throw new EmptyReturnException("2", "No hay criterios, por lo menos tiene que haber uno");//A la hora de  crear rubrica tiene que haber por lo menos un criterio
        }

        List<CalificationRegisterEntity> califications = calificationRegisterRepository
                .findCalificationsByStudentAndSubject(idStudent, idSubject, semester, idRubric);


        EvaluationResponseViewDTO response = new EvaluationResponseViewDTO();

        response.setDescription(rubricDescription);

        BigDecimal totalScore = evaluationRepository
                .findEvaluationsByStudentAndSubject(idStudent, idSubject, semester, idRubric)
                .orElseThrow(() -> new EmptyReturnException("3","No existe la evaluacion"));
        response.setTotalScore(totalScore);

        //Mapear criterios con sus niveles
        List<CriteriaResponseViewDTO> criteriaDTOs = criterias.stream()
                .map(criteria -> {
                    CriteriaResponseViewDTO dto = new CriteriaResponseViewDTO();
                    dto.setDescriptionCriteria(criteria.getCrfDescripcion());
                    dto.setPercentage(criteria.getCrfPorcentaje());

                    // Mapear niveles con sus rangos
                    List<PerformanceLevelResponseViewDTO> levels = criteria.getNiveles().stream()
                            .map(level -> {
                                PerformanceLevelResponseViewDTO levelDto = new PerformanceLevelResponseViewDTO();
//                                levelDto.setName(level.getName());
                                levelDto.setDescription(level.getNivelDescripcion());
                                levelDto.setRange(level.getRangoNota()); // Asegurando que se mapee el rango
                                return levelDto;
                            })
                            .collect(Collectors.toList());
                    dto.setLevels(levels);

                    return dto;
                })
                .collect(Collectors.toList());
        response.setCriterias(criteriaDTOs);

     //    Mapear calificaciones
        List<CalificationResponseViewDTO> calificationDTOs = califications.stream()
                .map(cal -> {
                    CalificationResponseViewDTO calDto = new CalificationResponseViewDTO();
                    calDto.setCalification(cal.getCalification());
                    calDto.setMessage(cal.getMessage());
                    calDto.setLevel(cal.getLevel());
                    cal.getEvaluation().getScore();

                    return calDto;
                })
                .collect(Collectors.toList());
        response.setCalifications(calificationDTOs);

        return response;

    }


}

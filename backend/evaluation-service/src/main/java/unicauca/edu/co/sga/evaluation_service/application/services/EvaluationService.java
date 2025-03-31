package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.CalificationResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.CriteriaResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.EvaluationResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.PerformanceLevelResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.*;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.*;

import java.util.List;
import java.util.stream.Collectors;

@Service

@Transactional
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final EnrollRepository enrollRepository;
    private final RubricRepository rubricRepository;
    private final CriteriaRepository criteriaRepository;
    private final CalificationRegisterRepository calificationRegisterRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository,
                             EnrollRepository enrollRepository,
                             RubricRepository rubricRepository, CriteriaRepository criteriaRepository, CalificationRegisterRepository calificationRegisterRepository, ModelMapper modelMapper) {
        this.evaluationRepository = evaluationRepository;
        this.enrollRepository = enrollRepository;
        this.rubricRepository = rubricRepository;
        this.criteriaRepository = criteriaRepository;
        this.calificationRegisterRepository = calificationRegisterRepository;
        this.modelMapper = modelMapper;
    }

    public EvaluationEntity createEvaluation(EvaluationRequestDTO evaluationRequestDTO) {
        // VERIFICACIONES
        EnrollEntity enroll = enrollRepository.findById(evaluationRequestDTO.getEnroll())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Enroll no encontrado con id: " + evaluationRequestDTO.getEnroll()));

        RubricEntity rubric = rubricRepository.findById(evaluationRequestDTO.getRubric())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rubric no encontrado con id: " + evaluationRequestDTO.getRubric()));

        // CREAR NUEVA EVALUACION
        EvaluationEntity evaluation = new EvaluationEntity();
        evaluation.setEnroll(enroll);
        evaluation.setRubric(rubric);
        evaluation.setDescription(evaluationRequestDTO.getDescription());

        return evaluationRepository.save(evaluation);
    }

    public EvaluationEntity getEvaluationById(Long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Evaluation no encontrada con id: " + id));
    }

    public EvaluationResponseViewDTO getEvaluationDetails(Long studentId, Long subjectId,
                                                          String semester, Long rubricId) {
        // 1. Buscar evaluaciones que cumplan todos los criterios
        List<EvaluationEntity> evaluations = evaluationRepository
                .findEvaluationsByStudentAndSubject(studentId,subjectId,semester,rubricId);

        if (evaluations.isEmpty()) {
            return null; // O lanzar excepción si prefieres
        }

        // 2. Buscar criterios con niveles (con los mismos filtros)
        List<CriteriaEntity> criteriaEntities = criteriaRepository
                .findByRubricAndStudentAndSubject(rubricId,studentId,subjectId,semester);

        // 3. Buscar calificaciones (con los mismos filtros)
        List<CalificationRegisterEntity> califications = calificationRegisterRepository
                .findCalificationsByStudentAndSubject(studentId, subjectId, semester, rubricId);
        EvaluationEntity evaluation = evaluations.get(0);

        // Mapeo a DTO
        EvaluationResponseViewDTO response = new EvaluationResponseViewDTO();
        response.setDescription(evaluation.getDescription());

        // Mapear criterios con sus niveles
        List<CriteriaResponseViewDTO> criteriaDTOs = criteriaEntities.stream()
                .map(criteria -> {
                    CriteriaResponseViewDTO dto = new CriteriaResponseViewDTO();
                    dto.setDescriptionCriteria(criteria.getDescription());
                    dto.setPercentage(criteria.getPercentage());

                    // Mapear niveles con sus rangos
                    List<PerformanceLevelResponseViewDTO> levels = criteria.getLevels().stream()
                            .map(level -> {
                                PerformanceLevelResponseViewDTO levelDto = new PerformanceLevelResponseViewDTO();
                                levelDto.setName(level.getName());
                                levelDto.setDescription(level.getDescription());
                                levelDto.setRange(level.getRango()); // Asegurando que se mapee el rango
                                return levelDto;
                            })
                            .collect(Collectors.toList());
                    dto.setLevels(levels);

                    return dto;
                })
                .collect(Collectors.toList());
        response.setCriterias(criteriaDTOs);

        // Mapear calificaciones
        List<CalificationResponseViewDTO> calificationDTOs = califications.stream()
                .map(cal -> {
                    CalificationResponseViewDTO calDto = new CalificationResponseViewDTO();
                    calDto.setCalification(cal.getCalification());
                    calDto.setMessage(cal.getMessage());
                    calDto.setLevel(cal.getLevel());
                    return calDto;
                })
                .collect(Collectors.toList());
        response.setCalifications(calificationDTOs);

        return response;


    }
    /*
    public EvaluationResponseViewDTO getEvaluationDetails(Long studentId, Long subjectId,
                                                          String semester, Long rubricId) {
        // 1. Obtener evaluación
        List<EvaluationEntity> evaluations = evaluationRepository
                .findEvaluationsByStudentAndSubject(studentId, subjectId, semester, rubricId);

        if (evaluations.isEmpty()) return null;

        EvaluationEntity evaluation = evaluations.get(0);

        // 2. Obtener criterios CON SUS NIVELES (usando fetch join)
        List<CriteriaEntity> criteriaEntities = criteriaRepository
                .findByRubricIdWithLevels(rubricId);

        // 3. Obtener calificaciones
        List<CalificationRegisterEntity> califications = calificationRegisterRepository
                .findByEvaluationId2(evaluation.getId());

        // Mapeo a DTO
        EvaluationResponseViewDTO response = new EvaluationResponseViewDTO();
        response.setDescription(evaluation.getDescription());

        // Mapear criterios con sus niveles
        List<CriteriaResponseViewDTO> criteriaDTOs = criteriaEntities.stream()
                .map(criteria -> {
                    CriteriaResponseViewDTO dto = new CriteriaResponseViewDTO();
                    dto.setDescriptionCriteria(criteria.getDescription());
                    dto.setPercentage(criteria.getPercentage());

                    // Mapear niveles con sus rangos
                    List<PerformanceLevelResponseViewDTO> levels = criteria.getLevels().stream()
                            .map(level -> {
                                PerformanceLevelResponseViewDTO levelDto = new PerformanceLevelResponseViewDTO();
                                levelDto.setName(level.getName());
                                levelDto.setDescription(level.getDescription());
                                levelDto.setRange(level.getRango()); // Asegurando que se mapee el rango
                                return levelDto;
                            })
                            .collect(Collectors.toList());
                    dto.setLevels(levels);

                    return dto;
                })
                .collect(Collectors.toList());
        response.setCriterias(criteriaDTOs);

        // Mapear calificaciones
        List<CalificationResponseViewDTO> calificationDTOs = califications.stream()
                .map(cal -> {
                    CalificationResponseViewDTO calDto = new CalificationResponseViewDTO();
                    calDto.setCalification(cal.getCalification());
                    calDto.setMessage(cal.getMessage());
                    calDto.setLevel(cal.getLevel());
                    return calDto;
                })
                .collect(Collectors.toList());
        response.setCalifications(calificationDTOs);

        return response;
    }*/
/*
    public EvaluationResponseViewDTO getEvaluationDetails(Long studentId, Long subjectId,
                                                          String semester, Long rubricId) throws ResourceNotFoundException {
        // 1. Obtener evaluación con los filtros
        List<EvaluationEntity> evaluations = evaluationRepository
                .findEvaluationsByStudentAndSubject(studentId, subjectId, semester, rubricId);

        if (evaluations.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró evaluación con los parámetros especificados");
        }

        EvaluationEntity evaluation = evaluations.get(0);

        // 2. Obtener criterios con niveles (incluyendo los filtros adicionales)
        List<CriteriaEntity> criteriaEntities = criteriaRepository
                .findByRubricAndStudentAndSubject(rubricId);

        // 3. Obtener calificaciones (incluyendo los filtros adicionales)
        List<CalificationRegisterEntity> califications = calificationRegisterRepository
                .findByEvaluationAndStudentAndSubject(evaluation.getId(), studentId, subjectId, semester);

        // Mapeo a DTO
        return mapToEvaluationResponse(evaluation, criteriaEntities, califications);
    }

    private EvaluationResponseViewDTO mapToEvaluationResponse(
            EvaluationEntity evaluation,
            List<CriteriaEntity> criteriaEntities,
            List<CalificationRegisterEntity> califications) {

        EvaluationResponseViewDTO response = new EvaluationResponseViewDTO();
        response.setDescription(evaluation.getDescription());

        // Mapear criterios
        List<CriteriaResponseViewDTO> criteriaDTOs = criteriaEntities.stream()
                .map(this::mapToCriteriaDTO)
                .collect(Collectors.toList());
        response.setCriterias(criteriaDTOs);

        // Mapear calificaciones
        List<CalificationResponseViewDTO> calificationDTOs = califications.stream()
                .map(this::mapToCalificationDTO)
                .collect(Collectors.toList());
        response.setCalifications(calificationDTOs);

        return response;
    }

    private CriteriaResponseViewDTO mapToCriteriaDTO(CriteriaEntity criteria) {
        CriteriaResponseViewDTO dto = new CriteriaResponseViewDTO();
        dto.setDescriptionCriteria(criteria.getDescription());
        dto.setPercentage(criteria.getPercentage());

        List<PerformanceLevelResponseViewDTO> levels = criteria.getLevels().stream()
                .map(this::mapToPerformanceLevelDTO)
                .collect(Collectors.toList());
        dto.setLevels(levels);

        return dto;
    }

    private PerformanceLevelResponseViewDTO mapToPerformanceLevelDTO(PerformanceEntity level) {
        PerformanceLevelResponseViewDTO dto = new PerformanceLevelResponseViewDTO();
        dto.setName(level.getName());
        dto.setDescription(level.getDescription());
        dto.setRange(level.getRango());
        return dto;
    }

    private CalificationResponseViewDTO mapToCalificationDTO(CalificationRegisterEntity calification) {
        CalificationResponseViewDTO dto = new CalificationResponseViewDTO();
        dto.setCalification(calification.getCalification());
        dto.setMessage(calification.getMessage());
        dto.setLevel(calification.getLevel());
        return dto;
    }*/
}

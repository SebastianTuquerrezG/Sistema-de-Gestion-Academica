package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.EvaluationResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.services.ResourceNotFoundException;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    //OBTENER EVALUACION POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationResponseDTO> getEvaluation(@PathVariable Long id) {
        EvaluationEntity evaluation = evaluationService.getEvaluationById(id);
        return ResponseEntity.ok(mapToResponseDTO(evaluation));
    }

    //GUARDAR EVALUACION
    @PostMapping("/save")
    public ResponseEntity<EvaluationResponseDTO> createEvaluation(
            @Valid @RequestBody EvaluationRequestDTO evaluationRequestDTO) {
        EvaluationEntity savedEvaluation = evaluationService.createEvaluation(evaluationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToResponseDTO(savedEvaluation));
    }

    private EvaluationResponseDTO mapToResponseDTO(EvaluationEntity entity) {
        return EvaluationResponseDTO.builder()
                .id(entity.getId())
                .enroll(entity.getEnroll().getId())
                .rubric(entity.getRubric().getId())
                .description(entity.getDescription())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .build();
    }
    // Otra versión con parámetros en el path
    @GetMapping("/students/{studentId}/subjects/{subjectId}/semesters/{semester}/rubrics/{rubricId}")
    public ResponseEntity<EvaluationResponseViewDTO> getEvaluationDetailsPath(
            @PathVariable Long studentId,
            @PathVariable Long subjectId,
            @PathVariable String semester,
            @PathVariable Long rubricId) throws ResourceNotFoundException {

        EvaluationResponseViewDTO response = evaluationService
                .getEvaluationDetails(studentId, subjectId, semester, rubricId);

        return ResponseEntity.ok(response);
    }
}


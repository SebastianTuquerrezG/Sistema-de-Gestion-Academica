package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public EvaluationResponseDTO getEvaluation(@PathVariable Long id) {
        return evaluationService.getEvaluationById(id);
    }

    //GUARDAR EVALUACION
    @PostMapping("/save")
    public ResponseEntity<EvaluationResponseDTO> createEvaluation(
            @Valid @RequestBody EvaluationRequestDTO requestDTO) {
        EvaluationResponseDTO response = evaluationService.createEvaluation(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
}


package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.EvaluationPort;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationPort evaluationPort;

    @GetMapping
    @ResponseStatus
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    public ResponseEntity<List<EvaluationResponseDTO>> getAllEvaluations() {
        List<EvaluationResponseDTO> evaluations = evaluationPort.getEvaluations();
        if (evaluations.isEmpty()) {
            throw new NotFoundException("Evaluations not found");
        }
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    public ResponseEntity<EvaluationResponseDTO> getEvaluation(@PathVariable Long id) {
        return evaluationPort.getEvaluationById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Evaluation " + id + " not found"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<EvaluationResponseDTO> createEvaluation(
            @Valid @RequestBody EvaluationRequestDTO requestDTO) {
        EvaluationResponseDTO response = evaluationPort.saveEvaluation(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<Boolean> updateEvaluation(@PathVariable Long id, @Valid @RequestBody EvaluationRequestDTO evaluationRequestDTO) {
        try {
            boolean isUpdated = evaluationPort.updateEvaluation(id, evaluationRequestDTO);
            return ResponseEntity.ok(isUpdated);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la evaluación", e);
        }
    }

    @GetMapping("/enroll/{enrollId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    public ResponseEntity<List<EvaluationResponseDTO>> getEvaluationsByEnroll(@PathVariable Long enrollId) {
        List<EvaluationResponseDTO> evaluations = evaluationPort.getEvaluationsByEnrollId(enrollId);
        if (evaluations.isEmpty()) {
            throw new NotFoundException("No evaluations found for enroll " + enrollId);
        }
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/rubric/{rubricId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    public ResponseEntity<List<EvaluationResponseDTO>> getEvaluationsByRubric(@PathVariable Long rubricId) {
        List<EvaluationResponseDTO> evaluations = evaluationPort.getEvaluationsByRubricId(rubricId);
        if (evaluations.isEmpty()) {
            throw new NotFoundException("No evaluations found for rubric " + rubricId);
        }
        return ResponseEntity.ok(evaluations);
    }
}


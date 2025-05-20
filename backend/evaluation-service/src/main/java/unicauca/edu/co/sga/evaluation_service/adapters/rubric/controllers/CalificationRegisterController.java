package unicauca.edu.co.sga.evaluation_service.adapters.rubric.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CalificationRegisterRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CalificationRegisterResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.CalificationRegisterPort;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calification-register")
public class CalificationRegisterController {
    private final CalificationRegisterPort calificationRegisterPort;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<List<CalificationRegisterResponseDTO>> getAllCalificationRegisters() {
        List<CalificationRegisterResponseDTO> calificationRegisters = calificationRegisterPort.getCalificationRegisters();
        return ResponseEntity.ok(calificationRegisters);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<CalificationRegisterResponseDTO> getCalificationRegisterById(@PathVariable Long id) {
        return calificationRegisterPort.getCalificationRegisterById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Calification register " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<CalificationRegisterResponseDTO> createCalificationRegister(@Valid @RequestBody CalificationRegisterRequestDTO calificationRegister) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calificationRegisterPort.saveCalificationRegister(calificationRegister));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<CalificationRegisterResponseDTO> updateCalificationRegister(@PathVariable Long id, @Valid @RequestBody CalificationRegisterRequestDTO calificationRegister) {
        boolean isUpdated = calificationRegisterPort.updateCalificationRegister(id, calificationRegister);
        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            throw new NotFoundException("Calification register " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Void> deleteCalificationRegister(@PathVariable Long id) {
        boolean isDeleted = calificationRegisterPort.deleteCalificationRegister(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new NotFoundException("Not found");
        }
    }

    @GetMapping("level/{level}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<List<CalificationRegisterResponseDTO>> getCalificationRegistersByLevel(@PathVariable String level) {
        List<CalificationRegisterResponseDTO> calificationRegisters = calificationRegisterPort.getCalificationRegisterByLevel(level);
        if (calificationRegisters.isEmpty()){
            throw new NotFoundException("No calification registers found for level " + level);
        }
        return ResponseEntity.ok(calificationRegisters);
    }

    @GetMapping("evaluation/{evaluationId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<List<CalificationRegisterResponseDTO>> getCalificationRegistersByEvaluationId(@PathVariable Long evaluationId) {
        List<CalificationRegisterResponseDTO> calificationRegisters = calificationRegisterPort.getCalificationRegisterByEvaluation(evaluationId);
        if (calificationRegisters.isEmpty()){
            throw new NotFoundException("No calification registers found for evaluation " + evaluationId);
        }
        return ResponseEntity.ok(calificationRegisters);
    }
}

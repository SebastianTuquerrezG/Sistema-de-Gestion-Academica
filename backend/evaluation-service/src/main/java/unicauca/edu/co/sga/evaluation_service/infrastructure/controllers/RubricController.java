package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RubricRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RubricPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import java.util.List;

// TODO: THIS CONTROLLER WOULD BE CHANGED FOR SHOW RUBRICS INSTEAD OF MANAGE IT.

@RestController
@RequiredArgsConstructor
@RequestMapping("/rubrics")
public class RubricController {

    private final RubricPort rubricPort;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<RubricResponseDTO>> getAllRubrics() {
        return ResponseEntity.ok(rubricPort.getRubrics());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<RubricResponseDTO> getRubricById(@PathVariable Long id) {
        return rubricPort.getRubricById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<RubricResponseDTO> createRubric(@RequestBody RubricRequestDTO rubric) {
        RubricResponseDTO created = rubricPort.saveRubric(rubric);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Void> updateRubric(@PathVariable Long id, @RequestBody RubricRequestDTO rubric) {
        boolean updated = rubricPort.updateRubric(id, rubric);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Void> deleteRubric(@PathVariable Long id) {
        boolean deleted = rubricPort.deleteRubric(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/subject/{id}")
    @ResponseStatus
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<RubricResponseDTO>> getRubricsBySubjectId(@PathVariable Long id){
        return ResponseEntity.ok(rubricPort.getRubricsBySubject(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public List<RubricResponseDTO> getRubricByName(@RequestParam String name) {
        List<RubricResponseDTO> rubrics = rubricPort.getRubricByName(name);
        if (rubrics.isEmpty()) {
            throw new RuntimeException("No rubrics found with name: " + name);
        }
        return rubrics;
    }

    @GetMapping("/ra/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<RubricResponseDTO>> getRubricsByRA(@PathVariable Long id) {
        return ResponseEntity.ok(rubricPort.getRubricsByRA(id));
    }

    @GetMapping("/criteria/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<RubricResponseDTO>> getRubricsByCriteria(@PathVariable Long id) {
        return ResponseEntity.ok(rubricPort.getRubricsByCriteria(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<RubricResponseDTO>> getRubricsByStatus(@PathVariable GeneralEnums.status status) {
        return ResponseEntity.ok(rubricPort.getRubricsByStatus(status));
    }

}

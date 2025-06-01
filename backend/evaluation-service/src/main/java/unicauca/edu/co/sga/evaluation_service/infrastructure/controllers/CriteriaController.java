package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CriteriaRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CriteriaResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.CriteriaPort;

import java.util.List;

@RestController
@RequestMapping("/criteria")
@RequiredArgsConstructor
public class CriteriaController {
    private final CriteriaPort criteriaPort;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<CriteriaResponseDTO>> getAllCriteria() {
        return ResponseEntity.ok(criteriaPort.getCriteria());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<CriteriaResponseDTO> getCriteriaById(@PathVariable Long id) {
        return criteriaPort.getCriteriaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN_ROLE')")
    public ResponseEntity<CriteriaResponseDTO> createCriteria(@RequestBody CriteriaRequestDTO criteria) {
        CriteriaResponseDTO created = criteriaPort.saveCriteria(criteria);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN_ROLE')")
    public ResponseEntity<Void> updateCriteria(@PathVariable Long id, @RequestBody CriteriaRequestDTO criteria) {
        boolean updated = criteriaPort.updateCriteria(id, criteria);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN_ROLE')")
    public ResponseEntity<Void> deleteCriteria(@PathVariable Long id) {
        boolean deleted = criteriaPort.deleteCriteria(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/rubric/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<CriteriaResponseDTO>> getCriteriaByRubric(@PathVariable Long id) {
        return ResponseEntity.ok(criteriaPort.getCriteriaByRubric(id));
    }
}

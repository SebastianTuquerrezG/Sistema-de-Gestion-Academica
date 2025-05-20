package unicauca.edu.co.sga.evaluation_service.adapters.rubric.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RubricPort;

import java.util.List;

// TODO: THIS CONTROLLER WOULD BE CHANGED FOR SHOW RUBRICS INSTEAD OF MANAGE IT.

@RestController
@RequiredArgsConstructor
@RequestMapping("/rubrics")
public class RubricController {

    private final RubricPort rubricPort;

    @GetMapping("/ping")
    @ResponseStatus
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Servicio Activo!");
    }

    @GetMapping("/subject/{id}")
    @ResponseStatus
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<List<RubricResponseDTO>> getRubricsBySubjectId(@PathVariable Long id){
        return ResponseEntity.ok(rubricPort.getRubricsBySubject(id));
    }
}

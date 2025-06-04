package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RARequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RAResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RAPort;

import java.util.List;

@RestController
@RequestMapping("/ra")
@RequiredArgsConstructor
public class RAController {
    private final RAPort raService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<List<RAResponseDTO>> getAllRAs() {
        return ResponseEntity.ok(raService.getRAs());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<RAResponseDTO> getRA(@PathVariable Long id) {
        return raService.getRAById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<RAResponseDTO> createRA(@RequestBody RARequestDTO requestDTO) {
        return ResponseEntity.ok(raService.saveRA(requestDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<Void> deleteRA(@PathVariable Long id) {
        return raService.deleteRA(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<List<RAResponseDTO>> getRAsByName(@PathVariable String name) {
        List<RAResponseDTO> ras = raService.getRAsByName(name);
        if (ras.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ras);
    }
}

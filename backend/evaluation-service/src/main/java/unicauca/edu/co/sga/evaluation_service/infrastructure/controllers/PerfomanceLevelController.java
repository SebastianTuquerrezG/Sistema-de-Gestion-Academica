package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.PerformanceLevelRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.PerformanceLevelResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.PerformanceLevelPort;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performance-levels")
public class PerfomanceLevelController {
    private final PerformanceLevelPort performanceLevelPort;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<PerformanceLevelResponseDTO>> getPerformances() {
        List<PerformanceLevelResponseDTO> performanceLevelResponseDTOS = performanceLevelPort.getPerformanceLevels();
        if (performanceLevelResponseDTOS.isEmpty()){
            throw new NotFoundException("Performances not found");
        }
        return ResponseEntity.ok(performanceLevelResponseDTOS);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<PerformanceLevelResponseDTO> getPerformancesById(@PathVariable Long id) {
        return performanceLevelPort.getPerformanceLevelById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Performance " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<PerformanceLevelResponseDTO> createPerformance(@Valid @RequestBody PerformanceLevelRequestDTO requestDTO) {
        PerformanceLevelResponseDTO responseDTO = performanceLevelPort.savePerformanceLevel(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Boolean> updateEvaluation(@PathVariable Long id, @Valid @RequestBody PerformanceLevelRequestDTO performanceLevelRequestDTO) {
        try {
            boolean isUpdated = performanceLevelPort.updatePerformanceLevel(id, performanceLevelRequestDTO);
            return ResponseEntity.ok(isUpdated);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el nivel", e);
        }
    }
}

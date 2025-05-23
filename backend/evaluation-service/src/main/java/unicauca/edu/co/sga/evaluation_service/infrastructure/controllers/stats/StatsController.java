package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.*;
import unicauca.edu.co.sga.evaluation_service.application.ports.StatsPort;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController {
    private final StatsPort statsPort;

    @PostMapping("/by-rubric")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<CourseStatsDTO> getStatsByRubric(@RequestBody FilterStatsDTO filter) {
        CourseStatsDTO stats = statsPort.getStatsByRubric(filter);
        return ResponseEntity.ok(stats);
    }

}

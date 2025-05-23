package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers.stats;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.*;
import unicauca.edu.co.sga.evaluation_service.application.ports.StatsPort;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final StatsPort statsPort;

    public StatsController(StatsPort statsPort) {
        this.statsPort = statsPort;
    }

    @PostMapping("/by-rubric")
    public ResponseEntity<CourseStatsDTO> getStatsByRubric(@RequestBody FilterStatsDTO filter) {
        CourseStatsDTO stats = statsPort.getStatsByRubric(filter);
        return ResponseEntity.ok(stats);
    }

}

package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.*;
import unicauca.edu.co.sga.evaluation_service.application.ports.StatsPort;
import unicauca.edu.co.sga.evaluation_service.application.services.stats.CriteriaStatsService;
import unicauca.edu.co.sga.evaluation_service.application.services.stats.HistogramCriteriaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController {
<<<<<<< Updated upstream
    private final StatsPort statsPort;
=======

    private final StatsPort statsPort;
    private final HistogramCriteriaService histogramCriteriaService;
    private final CriteriaStatsService criteriaStatsService;
    public StatsController(StatsPort statsPort, HistogramCriteriaService histogramCriteriaService, CriteriaStatsService criteriaStatsService) {
        this.statsPort = statsPort;
        this.histogramCriteriaService = histogramCriteriaService;
        this.criteriaStatsService = criteriaStatsService;
    }
>>>>>>> Stashed changes

    @PostMapping("/by-rubric")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<CourseStatsDTO> getStatsByRubric(@RequestBody FilterStatsDTO filter) {
        CourseStatsDTO stats = statsPort.getStatsByRubric(filter);
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/by-criteria")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<HistogramByCriteriaDTO>> getHistogramByCriteria(
            @RequestBody FilterStatsDTO filter) {

        List<HistogramByCriteriaDTO> histogram = histogramCriteriaService.getHistogramByCriteria(
                filter.getSubjectName(),
                filter.getRubricName(),
                filter.getPeriod()
        );
        return ResponseEntity.ok(histogram);
    }


    @PostMapping("/criteria-stats")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<List<CriteriaAverageDTO>> getCriteriaAverages(@RequestBody FilterStatsDTO filter) {
        List<CriteriaAverageDTO> result = criteriaStatsService.getCriteriaAverages(
                filter.getSubjectName(),
                filter.getRubricName(),
                filter.getPeriod()
        );
        return ResponseEntity.ok(result);
    }
}

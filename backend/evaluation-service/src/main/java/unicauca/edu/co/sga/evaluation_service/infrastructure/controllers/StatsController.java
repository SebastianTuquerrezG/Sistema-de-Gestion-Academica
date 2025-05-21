package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriterionAverageDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriterionHistogramDTO;
import unicauca.edu.co.sga.evaluation_service.application.services.CourseStatsService;
import unicauca.edu.co.sga.evaluation_service.application.services.CriterionHistogramService;
import unicauca.edu.co.sga.evaluation_service.application.services.CriterionStatsService;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor

public class StatsController {

    //private final EvaluationRepository evaluationRepository;
    private final CriterionStatsService criterionStatsService;
    private final CriterionHistogramService histogramService;
    private final CourseStatsService courseStatsService;

    /*public StatsController(
            EvaluationRepository evaluationRepository,
            CriterionStatsService criterionStatsService,
            CriterionHistogramService histogramService
    ) {
        this.evaluationRepository = evaluationRepository;
        this.criterionStatsService = criterionStatsService;
        this.histogramService = histogramService;
    }*/

    //PROMEDIO DE NOTA POR CRITERIO
    @GetMapping("/{rubricId}/criteria-averages")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public List<CriterionAverageDTO> getAveragesByCriteria(@PathVariable Long rubricId) {
        return criterionStatsService.calculateAveragesByRubric(rubricId);
    }

    //ESTUDIANTES POR CRITERIOS
    @GetMapping("/criteria/{criterionId}/histogram")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<CriterionHistogramDTO> getCriterionHistogram(
            @PathVariable Long criterionId) {
        return ResponseEntity.ok(histogramService.getHistogramData(criterionId));
    }

    //ESTADITICAS GENERALES
    @GetMapping("/{courseId}/statistics")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<CourseStatsDTO> getCourseStatistics(
            @PathVariable Long courseId,
            @RequestParam Long rubricId,
            @RequestParam String semester) {

        return ResponseEntity.ok(
                courseStatsService.calculateCourseStats(courseId, rubricId, semester)
        );
    }

}

package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers.stats;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.FilterStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramByCriteriaDTO;
import unicauca.edu.co.sga.evaluation_service.application.services.stats.HistogramCriteriaService;

import java.util.List;

@RestController
@RequestMapping("/histogram-criteria")
public class HistogramCriteriaController {
/*
    private final HistogramCriteriaService service;

    public HistogramCriteriaController(HistogramCriteriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<List<HistogramByCriteriaDTO>> getHistogram(@RequestBody FilterStatsDTO filters) {
        List<HistogramByCriteriaDTO> data = service.getHistogramByCriteria(filters);
        return ResponseEntity.ok(data);
    }*/
}


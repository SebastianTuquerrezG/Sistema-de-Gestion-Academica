package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import unicauca.edu.co.sga.evaluation_service.application.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/{id}")
    public EvaluationEntity getEvaluation(@PathVariable Long id) {
        return evaluationService.getEvaluationById(id);
    }
}
    /*@Autowired
    private EvaluationRepository evaluationRepository;

    // Guardar
    @PostMapping
    public Evaluation createEvaluation(@RequestBody Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    // Obtener
    @GetMapping
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    // Obtener por id
    @GetMapping("/{id}")
    public Optional<Evaluation> getEvaluationById(@PathVariable Long id) {
        return evaluationRepository.findById(id);
    }
    }*/
    //NO BORRAR


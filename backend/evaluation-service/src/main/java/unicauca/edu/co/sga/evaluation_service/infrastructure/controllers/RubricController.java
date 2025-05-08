package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RubricPort;

import java.util.List;

// TODO: THIS CONTROLLER WOULD BE CHANGED FOR SHOW RUBRICS INSTEAD OF MANAGE IT.

@RestController
@RequiredArgsConstructor
@RequestMapping("/rubrics")
@CrossOrigin(value = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RubricController {

    private final RubricPort rubricPort;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Servicio Activo!");
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<List<RubricResponseDTO>> getRubricsBySubjectId(@PathVariable Long id){
        return ResponseEntity.ok(rubricPort.getRubricsBySubject(id));
    }
}

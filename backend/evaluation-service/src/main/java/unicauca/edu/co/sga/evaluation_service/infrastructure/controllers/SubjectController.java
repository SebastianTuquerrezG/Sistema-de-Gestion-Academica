package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.SubjectResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.SubjectPort;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectPort subjectPort;

    @GetMapping
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects(){
        return ResponseEntity.ok(subjectPort.getSubjects());
    }
}

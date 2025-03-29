package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.EnrollPort;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.enroll.EnrollAlreadyExistExcetion;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.enroll.EnrollNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enroll")
public class EnrollController {
    private final EnrollPort enrollPort;

    @GetMapping
    public ResponseEntity<List<EnrollResponseDTO>> getAllEnrolls(){
        return ResponseEntity.ok(enrollPort.getEnrolls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollResponseDTO> getEnrollById(@PathVariable Long id){
        return enrollPort.getEnrollsById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EnrollNotFoundException("Enroll with ID " + id + " not found."));
    }

    @PostMapping
    public ResponseEntity<EnrollResponseDTO> createEnroll(@Valid @RequestBody EnrollRequestDTO enroll){
        List<EnrollResponseDTO> existingSemester = enrollPort.getEnrollsBySemester(enroll.getSemester());
        if (existingSemester.isEmpty()){
            return ResponseEntity.status(HttpStatus.CREATED).body(enrollPort.saveEnroll(enroll));
        }
        throw new EnrollAlreadyExistExcetion("Enroll with ID " + existingSemester.getFirst().getId() + " already exist.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateEnroll(@PathVariable Long id, @Valid @RequestBody EnrollRequestDTO enroll){
        Optional<EnrollResponseDTO> enrollExist = enrollPort.getEnrollsById(id);
        if (enrollExist.isPresent()){
            boolean isUpdated = enrollPort.updateEnroll(id, enroll);
            return ResponseEntity.ok(isUpdated);
        }
        throw new EnrollNotFoundException("Enroll with ID " + id + " not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEnroll(@PathVariable Long id){
        boolean isDeleted = enrollPort.deleteEnroll(id);
        if (!isDeleted){
            throw new EnrollNotFoundException("Enroll with ID " + id + " not found.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/enrollByStudent/{id}")
    public ResponseEntity<EnrollResponseDTO> getEnrollsByStudentId(@PathVariable Long id){
        List<EnrollResponseDTO> enrollResponseDTO = enrollPort.getEnrollsByStudentId(id);
        if (enrollResponseDTO.isEmpty()){
            throw new EnrollNotFoundException("Student with ID " + id + " not found.");
        }
        return ResponseEntity.ok(enrollPort.getEnrollsByStudentId(id).getFirst());
    }
}

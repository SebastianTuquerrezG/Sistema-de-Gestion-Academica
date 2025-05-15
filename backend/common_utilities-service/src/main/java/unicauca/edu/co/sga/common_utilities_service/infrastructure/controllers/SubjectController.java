package unicauca.edu.co.sga.common_utilities_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.SubjectRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.SubjectResponseDTO;
import unicauca.edu.co.sga.common_utilities_service.application.ports.SubjectPort;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.common_utilities_service.domain.exceptions.AlreadyExistException;
import unicauca.edu.co.sga.common_utilities_service.domain.exceptions.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
@CrossOrigin(value = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SubjectController {
    private final SubjectPort subjectPort;

    @GetMapping
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects(){
        return ResponseEntity.ok(subjectPort.getSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(@PathVariable Long id){
        return subjectPort.getSubjectById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Subject " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<SubjectResponseDTO> createSubject(@Valid @RequestBody SubjectRequestDTO subject){
        Optional<SubjectResponseDTO> existingSubject = subjectPort.getByName(subject.getName());
        if (existingSubject.isPresent()) {
            throw new AlreadyExistException("Subject " + subject.getName() + " already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectPort.saveSubject(subject));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequestDTO subject){
        Optional<SubjectResponseDTO> existingSubject = subjectPort.getSubjectById(id);
        if (existingSubject.isPresent()){
            boolean isUpdated = subjectPort.updateSubject(id, subject);
            return ResponseEntity.ok(isUpdated);
        }
        throw new NotFoundException("Subject with ID " + id + " not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSubject(@PathVariable Long id){
        boolean isDeleted = subjectPort.deleteSubject(id);
        if (!isDeleted) {
            throw new NotFoundException("Subject " + id + " not found");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Optional<SubjectResponseDTO>> getSubjectsByName(@RequestParam String name){
        Optional<SubjectResponseDTO> subjects = subjectPort.getByName(name);
        if (subjects.isEmpty()) {
            throw new NotFoundException("No subjects found with name: " + name);
        }
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/status/{type}")
    public ResponseEntity<List<SubjectResponseDTO>> getStudentByStatus(@PathVariable GeneralEnums.status type){
        List<SubjectResponseDTO> subjects = subjectPort.getByStatus(type);
        if (subjects.isEmpty()) {
            throw new NotFoundException("No subjects found with that status type: " + type);
        }
        return ResponseEntity.ok(subjects);
    }

}

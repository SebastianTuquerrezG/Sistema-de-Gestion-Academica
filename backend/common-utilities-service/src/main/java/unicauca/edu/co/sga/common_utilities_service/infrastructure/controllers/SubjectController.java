package unicauca.edu.co.sga.common_utilities_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class SubjectController {
    private final SubjectPort subjectPort;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects(){
        return ResponseEntity.ok(subjectPort.getSubjects());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(@PathVariable Long id){
        return subjectPort.getSubjectById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Subject " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<SubjectResponseDTO> createSubject(@Valid @RequestBody SubjectRequestDTO subject){
        Optional<SubjectResponseDTO> existingSubject = subjectPort.getByName(subject.getName());
        if (existingSubject.isPresent()) {
            throw new AlreadyExistException("Subject " + subject.getName() + " already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectPort.saveSubject(subject));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Boolean> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequestDTO subject){
        Optional<SubjectResponseDTO> existingSubject = subjectPort.getSubjectById(id);
        if (existingSubject.isPresent()){
            boolean isUpdated = subjectPort.updateSubject(id, subject);
            return ResponseEntity.ok(isUpdated);
        }
        throw new NotFoundException("Subject with ID " + id + " not found.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Boolean> deleteSubject(@PathVariable Long id){
        boolean isDeleted = subjectPort.deleteSubject(id);
        if (!isDeleted) {
            throw new NotFoundException("Subject " + id + " not found");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<Optional<SubjectResponseDTO>> getSubjectsByName(@RequestParam String name){
        Optional<SubjectResponseDTO> subjects = subjectPort.getByName(name);
        if (subjects.isEmpty()) {
            throw new NotFoundException("No subjects found with name: " + name);
        }
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/status/{type}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<List<SubjectResponseDTO>> getStudentByStatus(@PathVariable GeneralEnums.status type){
        List<SubjectResponseDTO> subjects = subjectPort.getByStatus(type);
        if (subjects.isEmpty()) {
            throw new NotFoundException("No subjects found with that status type: " + type);
        }
        return ResponseEntity.ok(subjects);
    }

}

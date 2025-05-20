package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.EnrollPort;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.AlreadyExistException;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enroll")
public class EnrollController {
    private final EnrollPort enrollPort;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<List<EnrollResponseDTO>> getAllEnrolls(){
        return ResponseEntity.ok(enrollPort.getEnrolls());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<EnrollResponseDTO> getEnrollById(@PathVariable Long id){
        return enrollPort.getEnrollsById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Enroll with ID " + id + " not found."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<EnrollResponseDTO> createEnroll(@Valid @RequestBody EnrollRequestDTO enroll){
        boolean exists = enrollPort.existsByStudentIdAndCourseIdAndSemester(enroll.getStudent(), enroll.getCourse(), enroll.getSemester());
        if (exists) {
            throw new AlreadyExistException("Enroll for student ID " + enroll.getStudent() + " in course ID " + enroll.getCourse() + " for semester " + enroll.getSemester() + " already exists.");
        }
        EnrollResponseDTO createdEnroll = enrollPort.saveEnroll(enroll);
        return new ResponseEntity<>(createdEnroll, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<Boolean> updateEnroll(@PathVariable Long id, @Valid @RequestBody EnrollRequestDTO enroll){
        Optional<EnrollResponseDTO> enrollExist = enrollPort.getEnrollsById(id);
        if (enrollExist.isPresent()){
            boolean isUpdated = enrollPort.updateEnroll(id, enroll);
            return ResponseEntity.ok(isUpdated);
        }
        throw new NotFoundException("Enroll with ID " + id + " not found.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<Boolean> deleteEnroll(@PathVariable Long id){
        boolean isDeleted = enrollPort.deleteEnroll(id);
        if (!isDeleted){
            throw new NotFoundException("Enroll with ID " + id + " not found.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<EnrollResponseDTO> getEnrollsByStudentId(@PathVariable Long id){
        List<EnrollResponseDTO> enrollResponseDTO = enrollPort.getEnrollsByStudentId(id);
        if (enrollResponseDTO.isEmpty()){
            throw new NotFoundException("Student with ID " + id + " not found.");
        }
        return ResponseEntity.ok(enrollResponseDTO.getFirst());
    }
}

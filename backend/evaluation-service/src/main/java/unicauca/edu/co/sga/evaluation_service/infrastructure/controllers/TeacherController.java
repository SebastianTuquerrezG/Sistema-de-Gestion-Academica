package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.TeacherRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.TeacherResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.TeacherPort;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.AlreadyExistException;
import unicauca.edu.co.sga.evaluation_service.domain.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

// TODO: NEED TO BE DELETING. THIS CONTROLLER IS INTO HELPER_SERVICE


@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherPort teacherPort;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers(){
        return ResponseEntity.ok(teacherPort.getTeachers());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Long id){
        return teacherPort.getTeacherById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Teacher with ID " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<TeacherResponseDTO> createTeacher(@Valid @RequestBody TeacherRequestDTO teacherRequestDTO){
        Optional<TeacherResponseDTO> teacherExist = teacherPort.getTeacherByIdentification(teacherRequestDTO.getIdentification());
        if (teacherExist.isPresent()){
            throw new AlreadyExistException("Teacher with Identification " + teacherExist.get().getIdentification() + " already exist.");
        }
        return ResponseEntity.ok(teacherPort.saveTeacher(teacherRequestDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<Boolean> updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherRequestDTO teacherRequestDTO){
        Optional<TeacherResponseDTO> teacher = teacherPort.getTeacherById(id);
        if (teacher.isPresent()){
            boolean isUpdated = teacherPort.updateTeacher(id, teacherRequestDTO);
            return ResponseEntity.ok(isUpdated);
        }
        throw new NotFoundException("Teacher with ID " + id + " not found.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<Boolean> deleteTeacher(@PathVariable Long id){
        boolean isDeleted = teacherPort.deleteTeacher(id);
        if (!isDeleted){
            throw new NotFoundException("Teacher with ID " + id + " not found.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<TeacherResponseDTO> getTeacherByCourseId(@PathVariable Long id){
        List<TeacherResponseDTO> teacher = teacherPort.getTeachersByCourse(id);
        if (teacher.isEmpty()){
            throw new NotFoundException("Teacher with Course ID " + id + " not found.");
        }
        return ResponseEntity.ok(teacher.getFirst());
    }

    @GetMapping("/identification/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR', 'ROLE_TEACHER')")
    public ResponseEntity<TeacherResponseDTO> getTeacherByIdentification(@PathVariable Long id){
        Optional<TeacherResponseDTO> teacher = teacherPort.getTeacherByIdentification(id);
        if (teacher.isEmpty()){
            throw new NotFoundException("Teacher with identification " + id + " not found.");
        }
        return ResponseEntity.ok(teacher.get());
    }
}

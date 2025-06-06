package unicauca.edu.co.sga.common_utilities_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.StudentRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.StudentResponseDTO;
import unicauca.edu.co.sga.common_utilities_service.application.ports.StudentPort;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.common_utilities_service.domain.exceptions.AlreadyExistException;
import unicauca.edu.co.sga.common_utilities_service.domain.exceptions.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentPort studentPort;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(){
        return ResponseEntity.ok(studentPort.getStudents());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id){
        return studentPort.getStudentById(id)
               .map(ResponseEntity::ok)
               .orElseThrow(() -> new NotFoundException("Student " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO student){
        Optional<StudentResponseDTO> existingStudent = studentPort.getStudentsByIdentification(student.getIdentification());
        if (existingStudent.isPresent()) {
            throw new AlreadyExistException("Student " + student.getIdentification() + " already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentPort.saveStudent(student));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Boolean> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO student){
        Optional<StudentResponseDTO> existingStudent = studentPort.getStudentsByIdentification(student.getIdentification());
        if (existingStudent.isPresent() && !existingStudent.get().getId().equals(id)) {
            throw new AlreadyExistException("Student with identification " + student.getIdentification() + " already exists");
        }
        boolean isUpdated = studentPort.updateStudent(id, student);
        if (!isUpdated) {
            throw new NotFoundException("Student " + id + " not found");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id){
        boolean isDeleted = studentPort.deleteStudent(id);
        if (!isDeleted) {
            throw new NotFoundException("Student " + id + " not found");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByName(@RequestParam String name){
        List<StudentResponseDTO> students = studentPort.getStudentsByName(name);
        if (students.isEmpty()) {
            throw new NotFoundException("No students found with name: " + name);
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/identification")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<StudentResponseDTO> getStudentByIdentification(@RequestParam Long studentId){
        Optional<StudentResponseDTO> student = studentPort.getStudentsByIdentification(studentId);
        if (student.isEmpty()) {
            throw new NotFoundException("Student with identification " + studentId + " not found");
        }
        return ResponseEntity.ok(student.get());
    }

    @GetMapping("/identification-type/{type}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<List<StudentResponseDTO>> getStudentByTypeId(@PathVariable GeneralEnums.identificationType type){
        List<StudentResponseDTO> students = studentPort.getStudentsByIdentificationType(type);
        if (students.isEmpty()) {
            throw new NotFoundException("No students found with identification type: " + type);
        }
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/courseAndPeriod/{courseId}/{period}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByCourseAndPeriod(@PathVariable Long courseId, @PathVariable String period){
        List<StudentResponseDTO> students = studentPort.getStudentsByCourseAndPeriod(courseId, period);
        if (students.isEmpty()) {
            throw new NotFoundException("No students found for course " + courseId + " and period " + period);
        }
        return ResponseEntity.ok(students);
    }
}

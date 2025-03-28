package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.StudentRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.StudentPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentPort studentPort;

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(){
        return ResponseEntity.ok(studentPort.getStudents());
    }

    @GetMapping
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id){
        return studentPort.getStudentById(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO student){
        return ResponseEntity.ok(studentPort.saveStudent(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO student){
        boolean isUpdated = studentPort.updateStudent(id, student);
        return isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id){
        boolean isDeleted = studentPort.deleteStudent(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByName(@RequestParam String name){
        return ResponseEntity.ok(studentPort.getStudentsByName(name));
    }

    @GetMapping("/identification")
    public ResponseEntity<StudentResponseDTO> getStudentByIdentification(@RequestParam Long studentId){
        Optional<StudentResponseDTO> student = studentPort.getStudentsByIdentification(studentId);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/identification-type/{type}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentByTypeId(@PathVariable GeneralEnums.identificationType type){
        return ResponseEntity.ok(studentPort.getStudentsByIdentificationType(type));
    }
}

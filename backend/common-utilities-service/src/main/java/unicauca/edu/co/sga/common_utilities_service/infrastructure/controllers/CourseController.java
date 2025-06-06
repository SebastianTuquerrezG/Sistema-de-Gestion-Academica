package unicauca.edu.co.sga.common_utilities_service.infrastructure.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.CourseRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.CourseResponseDTO;
import unicauca.edu.co.sga.common_utilities_service.application.ports.CoursePort;
import unicauca.edu.co.sga.common_utilities_service.domain.exceptions.AlreadyExistException;
import unicauca.edu.co.sga.common_utilities_service.domain.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CoursePort coursePort;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<List<CourseResponseDTO>> getAllCourse(){
        return ResponseEntity.ok(coursePort.getCourses());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id){
        return coursePort.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Course with ID "+ id + " not found."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO requestDTO){
        List<CourseResponseDTO> course = coursePort.getCoursesBySubjectId(requestDTO.getSubject());
        if (course.isEmpty()){
            return ResponseEntity.status(HttpStatus.CREATED).body(coursePort.saveCourse(requestDTO));
        }
        throw new AlreadyExistException("Course with subject " + requestDTO.getSubject() + " already exist.");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Boolean> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO courseRequestDTO){
        Optional<CourseResponseDTO> course = coursePort.getCourseById(id);
        if (course.isPresent()){
            boolean isUpdated = coursePort.updateCourse(id, courseRequestDTO);
            return ResponseEntity.ok(isUpdated);
        }
        throw new NotFoundException("Course with ID " + id + " not found.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE')")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable Long id){
        boolean isDeleted = coursePort.deleteCourse(id);
        if (!isDeleted){
            throw new NotFoundException("Course with ID " + id + " not found.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/subject/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<CourseResponseDTO> getCourseBySubjectId(@PathVariable Long id){
        List<CourseResponseDTO> course = coursePort.getCoursesBySubjectId(id);
        if (course.isEmpty()){
            throw new NotFoundException("Course with subject ID " + id + " not found.");
        }
        return ResponseEntity.ok(course.getFirst());
    }

    @GetMapping("/ra/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<CourseResponseDTO> getCourseByRaId(@PathVariable Long id){
        List<CourseResponseDTO> course = coursePort.getCoursesByRAId(id);
        if (course.isEmpty()){
            throw new NotFoundException("Course with ra ID " + id + " not found.");
        }
        return ResponseEntity.ok(course.getFirst());
    }

    @GetMapping("/teacher/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_STUDENT_ROLE', 'ROLE_COORDINATOR_ROLE')")
    public ResponseEntity<CourseResponseDTO> getCourseByTeacherId(@PathVariable Long id){
        List<CourseResponseDTO> course = coursePort.getCoursesByTeacherId(id);
        if (course.isEmpty()){
            throw new NotFoundException("Course with teacher ID " + id + " not found.");
        }
        return ResponseEntity.ok(course.getFirst());
    }
}

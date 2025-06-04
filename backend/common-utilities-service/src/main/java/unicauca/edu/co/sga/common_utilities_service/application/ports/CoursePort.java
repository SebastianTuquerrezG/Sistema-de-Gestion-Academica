package unicauca.edu.co.sga.common_utilities_service.application.ports;

import unicauca.edu.co.sga.common_utilities_service.application.dto.request.CourseRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.CourseResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CoursePort {
    List<CourseResponseDTO> getCourses();
    Optional<CourseResponseDTO> getCourseById(Long id);
    CourseResponseDTO saveCourse(CourseRequestDTO course);
    boolean deleteCourse(Long id);
    boolean updateCourse(Long id, CourseRequestDTO course);
    List<CourseResponseDTO> getCoursesBySubjectId(Long id);
    List<CourseResponseDTO> getCoursesByRAId(Long id);
    List<CourseResponseDTO> getCoursesByTeacherId(Long id);
}

package unicauca.edu.co.sga.evaluation_service.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CourseRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CourseResponseDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.TeacherEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CourseRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    // TODO: Create the RA service to use it here.

    public CourseService(CourseRepository courseRepository, TeacherService teacherService, SubjectService subjectService){
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    public CourseResponseDTO saveCourseService(CourseRequestDTO courseRequestDTO){
        /*Optional<TeacherEntity> teacher = teacherService.getTeacherById(courseRequestDTO.getTeacher());
        Optional<SubjectEntity> subject = subjectService.getSubjectById(courseRequestDTO.getSubject());

        if (teacher.isEmpty() || subject.isEmpty()){
            return null;
        }
        CourseEntity newCourse = new CourseEntity();
        newCourse.setTeacher(Set.of(teacher.get()));
        newCourse.setSubject(subject.get());
        courseRepository.save(courseRequestDTO);*/
        // TODO: Create the logic when save a TEACHER and SUBJECT
        //courseRepository.save(courseRequestDTO);
        return null;
    }

}

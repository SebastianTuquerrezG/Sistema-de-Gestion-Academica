package unicauca.edu.co.sga.evaluation_service.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.TeacherRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.TeacherResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.TeacherPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.domain.enums.TeacherEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.TeacherEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService implements TeacherPort {
    @Autowired
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherResponseDTO> getTeachers(){
        // Get all the teachers from the repository
        List<TeacherEntity> teachers = teacherRepository.findAll();
        // Save it into TeacherResponseDTO.
        return teachers.stream()
                .map(teacher -> TeacherResponseDTO.builder()
                        .id(teacher.getId())
                        .name(teacher.getName())
                        .identification(teacher.getIdentification())
                        .teacherType(teacher.getTeacherType())
                        .degree(teacher.getDegree())
                        .status(teacher.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TeacherEntity> getTeacherById(Long id){
        return teacherRepository.findById(id);
    }

    @Override
    public TeacherResponseDTO saveTeacher(TeacherRequestDTO teacher) {
        return null;
    }

    @Override
    public boolean deleteTeacher(Long id) {
        return false;
    }

    @Override
    public boolean updateTeacher(Long id, TeacherRequestDTO teacher) {
        return false;
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByName(String name) {
        return List.of();
    }

    @Override
    public Optional<TeacherResponseDTO> getTeacherByIdentification(Long identification) {
        return Optional.empty();
    }

    @Override
    public Optional<TeacherResponseDTO> getTeacherByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByDegree(String degree) {
        return List.of();
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByIdentificationType(GeneralEnums.identificationType identificationType) {
        return List.of();
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByStatus(GeneralEnums.status status) {
        return List.of();
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByTeacherType(TeacherEnums teacherType) {
        return List.of();
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByCourse(Long courseId) {
        return List.of();
    }
}

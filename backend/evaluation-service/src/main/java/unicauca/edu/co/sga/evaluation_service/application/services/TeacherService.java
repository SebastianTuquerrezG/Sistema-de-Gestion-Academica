package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.TeacherRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.TeacherResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.TeacherPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.domain.enums.TeacherEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.TeacherEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.TeacherMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService implements TeacherPort {
    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherResponseDTO> getTeachers(){
        return teacherRepository.findAll().stream()
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TeacherResponseDTO> getTeacherById(Long id){
        return teacherRepository.findById(id)
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO);
    }

    @Override
    public TeacherResponseDTO saveTeacher(TeacherRequestDTO teacher) {
        TeacherEntity teacherEntity = TeacherMapper.toEntity(TeacherMapper.toModel(teacher));
        return TeacherMapper.toDTO(TeacherMapper.toModel(teacherRepository.save(teacherEntity)));
    }

    @Override
    public boolean deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateTeacher(Long id, TeacherRequestDTO teacher) {
        Optional<TeacherEntity> teacherExist = teacherRepository.findById(id);
        if(teacherExist.isPresent()){
            TeacherEntity teacherEntity = teacherExist.orElseThrow(() -> new RuntimeException("Teacher not found"));
            teacherEntity.setName(teacher.getName());
            teacherEntity.setIdentification(teacher.getIdentification());
            teacherEntity.setIdentificationType(teacher.getIdentificationType());
            teacherEntity.setDegree(teacher.getDegree());
            teacherEntity.setTeacherType(teacher.getTeacherType());
            teacherEntity.setStatus(teacher.getStatus());
            teacherRepository.save(teacherEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<TeacherResponseDTO> getTeacherByIdentification(Long identification) {
        return teacherRepository.findByIdentification(identification)
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO);
    }

    @Override
    public List<TeacherResponseDTO> getTeacherByName(String name) {
        return teacherRepository.findByNameContainingIgnoreCase(name).stream()
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByDegree(String degree) {
        return teacherRepository.findByDegreeContainingIgnoreCase(degree).stream()
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByIdentificationType(GeneralEnums.identificationType identificationType) {
        return teacherRepository.findByIdentificationType(identificationType).stream()
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByStatus(GeneralEnums.status status) {
        return teacherRepository.findByStatus(status).stream()
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByTeacherType(TeacherEnums teacherType) {
        return teacherRepository.findByTeacherType(teacherType).stream()
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherResponseDTO> getTeachersByCourse(Long courseId) {
        return teacherRepository.findByCourseId(courseId).stream()
                .map(TeacherMapper::toModel)
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }
}

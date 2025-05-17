package unicauca.edu.co.sga.common_utilities_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.SubjectRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.SubjectResponseDTO;
import unicauca.edu.co.sga.common_utilities_service.application.ports.SubjectPort;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.common_utilities_service.domain.models.Subject;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.mappers.SubjectMapper;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.repositories.SubjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectService implements SubjectPort {

    private final SubjectRepository subjectRepository;
    private final RabbitService rabbitService;

    @Override
    public List<SubjectResponseDTO> getSubjects(){
        List<SubjectEntity> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(subject -> SubjectResponseDTO.builder()
                        .id(subject.getId())
                        .name(subject.getName())
                        .credits(subject.getCredits())
                        .objective(subject.getObjectives())
                        .status(subject.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SubjectResponseDTO> getSubjectById(Long id) {
        List<SubjectResponseDTO> subject = getSubjects();
        return subject.stream()
                .filter(Subject -> Subject.getId().equals(id))
                .findFirst();
    }

    @Override
    public SubjectResponseDTO saveSubject(SubjectRequestDTO subjectDTO) {
        Subject subject = SubjectMapper.toModel(subjectDTO);
        SubjectEntity subjectEntity = SubjectMapper.toEntity(subject);

        //Sending message for Evaluation_service
        rabbitService.sendSubject(subjectDTO);

        return SubjectMapper.toDTO(
                SubjectMapper.toModel(
                        subjectRepository.save(subjectEntity)));
    }

    @Override
    public boolean deleteSubject(Long subjectId) {
        if (subjectRepository.existsById(subjectId)) {
            subjectRepository.deleteById(subjectId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateSubject(Long subjectId, SubjectRequestDTO subject) {
        Optional<SubjectEntity> subjectExist = subjectRepository.findById(subjectId);
        if(subjectExist.isPresent()){
            SubjectEntity subjectEntity = subjectExist.orElseThrow(() -> new RuntimeException("Subject not found"));
            subjectEntity.setName(subject.getName());
            subjectEntity.setCredits(subject.getCredits());
            subjectEntity.setObjectives(subject.getObjectives());
            subjectEntity.setStatus(subject.getStatus());
            subjectRepository.save(subjectEntity);
            return true;
        }
        return false;
    }

    @Override
    public Optional<SubjectResponseDTO> getByName(String name) {
        return subjectRepository.findByNameContainingIgnoreCase(name).stream()
                .map(SubjectMapper::toModel)
                .map(SubjectMapper::toDTO)
                .findFirst();
    }

    @Override
    public List<SubjectResponseDTO> getByStatus(GeneralEnums.status status) {
        return subjectRepository.findByStatus(status).stream()
                .map(SubjectMapper::toModel)
                .map(SubjectMapper::toDTO)
                .collect(Collectors.toList());
    }

}

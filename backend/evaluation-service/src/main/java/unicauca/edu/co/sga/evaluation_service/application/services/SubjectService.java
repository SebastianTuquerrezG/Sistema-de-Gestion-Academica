package unicauca.edu.co.sga.evaluation_service.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.SubjectRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.SubjectResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.SubjectPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.SubjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService implements SubjectPort {

    @Autowired
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

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
    public SubjectResponseDTO saveSubject(SubjectRequestDTO subject) {
        return null;
    }

    @Override
    public boolean deleteSubject(Long id) {
        return false;
    }

    @Override
    public boolean updateSubject(Long id, SubjectRequestDTO subject) {
        return false;
    }

    @Override
    public Optional<SubjectResponseDTO> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<SubjectResponseDTO> getByStatus(GeneralEnums.status status) {
        return List.of();
    }


}

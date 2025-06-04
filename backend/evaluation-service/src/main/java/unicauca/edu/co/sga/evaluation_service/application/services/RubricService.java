package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RubricRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RubricPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.domain.models.Rubric;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RAEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.RubricMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RARepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.SubjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RubricService implements RubricPort {

    private final RubricRepository rubricRepository;
    private final RubricMapper rubricMapper;
    private final RARepository raRepository;
    private final SubjectRepository  subjectRepository;

    @Override
    public List<RubricResponseDTO> getRubrics() {
        return rubricRepository.findAll().stream()
                .map(RubricMapper::toModel)
                .map(rubricMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<RubricResponseDTO> getRubricById(Long id) {
        return rubricRepository.findById(id)
                .map(RubricMapper::toModel)
                .map(rubricMapper::toDTO);
    }

    @Override
    public RubricResponseDTO saveRubric(RubricRequestDTO rubric) {
        Rubric rubricModel = RubricMapper.toModel(rubric);
        RubricEntity rubricEntity = RubricMapper.toEntity(rubricModel);
        return rubricMapper.toDTO(
                RubricMapper.toModel(
                        rubricRepository.save(rubricEntity)));
    }

    @Override
    public boolean deleteRubric(Long id) {
        if (rubricRepository.existsById(id)) {
            rubricRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateRubric(Long id, RubricRequestDTO rubric) {
        Optional<RubricEntity> existingRubric = rubricRepository.findById(id);
        if (existingRubric.isPresent()) {
            RubricEntity rubricEntity = existingRubric.orElseThrow(() -> new RuntimeException("Rubric not found"));
            Optional<RAEntity> raExist = raRepository.findById(rubric.getRaId());
            Optional<SubjectEntity> subjectExist = subjectRepository.findById(rubric.getIdMateria());
            rubricEntity.setNombreRubrica(rubric.getNombreRubrica());
            rubricEntity.setObjetivoEstudio(rubric.getObjetivoEstudio());
            rubricEntity.setNotaRubrica(rubric.getNotaRubrica());
            rubricEntity.setRa(raExist.orElse(null));
            rubricEntity.setSubject(subjectExist.orElse(null));
            rubricRepository.save(rubricEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<RubricResponseDTO> getRubricByName(String name) {
        List<RubricEntity> rubrics = rubricRepository.findByNombreRubricaContainingIgnoreCase(name);
        if (rubrics.isEmpty()) {
            throw new RuntimeException("No rubrics found with name: " + name);
        }
        return rubrics.stream()
                .map(RubricMapper::toModel)
                .map(rubricMapper::toDTO)
                .toList();
    }

    @Override
    public List<RubricResponseDTO> getRubricsBySubject(Long id) {
        return rubricRepository.findBySubjectId(id).stream()
                .map(RubricMapper::toModel)
                .map(rubricMapper::toDTO)
                .toList();
    }

    @Override
    public List<RubricResponseDTO> getRubricsByRA(Long id) {
        return rubricRepository.findByRaId(id).stream()
                .map(RubricMapper::toModel)
                .map(rubricMapper::toDTO)
                .toList();
    }

    @Override
    public List<RubricResponseDTO> getRubricsByCriteria(Long id) {
        return List.of();
    }

    @Override
    public List<RubricResponseDTO> getRubricsByStatus(GeneralEnums.status status) {
        return rubricRepository.findByEstado(status).stream()
                .map(RubricMapper::toModel)
                .map(rubricMapper::toDTO)
                .toList();
    }
}
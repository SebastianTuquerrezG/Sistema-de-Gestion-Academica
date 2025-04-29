package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CalificationRegisterRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CalificationRegisterResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.CalificationRegisterPort;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.CalificationRegisterMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CalificationRegisterRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CalificationRegisterService implements CalificationRegisterPort {
    private final CalificationRegisterRepository calificationRegisterRepository;
    private final EvaluationRepository evaluationRepository;

    @Override
    public List<CalificationRegisterResponseDTO> getCalificationRegisters() {
        return calificationRegisterRepository.findAll().stream()
                .map(CalificationRegisterMapper::toModel)
                .map(CalificationRegisterMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CalificationRegisterResponseDTO> getCalificationRegisterById(Long id) {
        return calificationRegisterRepository.findById(id)
                .map(CalificationRegisterMapper::toModel)
                .map(CalificationRegisterMapper::toDTO);
    }

    @Override
    public CalificationRegisterResponseDTO saveCalificationRegister(CalificationRegisterRequestDTO calificationRegister) {
        EvaluationEntity evaluationEntity = evaluationRepository.findById(calificationRegister.getEvaluationId())
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));

        CalificationRegisterEntity calificationRegisterEntity = CalificationRegisterEntity.builder()
                .calification(calificationRegister.getCalification())
                .message(calificationRegister.getMessage())
                .level(calificationRegister.getLevel())
                .evaluation(evaluationEntity)
                .build();

        CalificationRegisterEntity savedEntity = calificationRegisterRepository.save(calificationRegisterEntity);

        return CalificationRegisterMapper.toDTO(CalificationRegisterMapper.toModel(savedEntity));
    }


    @Override
    public boolean deleteCalificationRegister(Long id) {
        if (calificationRegisterRepository.existsById(id)) {
            calificationRegisterRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateCalificationRegister(Long id, CalificationRegisterRequestDTO calificationRegister) {
        Optional<CalificationRegisterEntity> calificationRegisterExist = calificationRegisterRepository.findById(id);
        if (calificationRegisterExist.isPresent()) {
            CalificationRegisterEntity calificationRegisterEntity = calificationRegisterExist.orElseThrow(() -> new RuntimeException("Calification register not found"));
            calificationRegisterEntity.setCalification(calificationRegister.getCalification());
            calificationRegisterEntity.setMessage(calificationRegister.getMessage());
            calificationRegisterEntity.setLevel(calificationRegister.getLevel());

            EvaluationEntity evaluationEntity = evaluationRepository.findById(calificationRegister.getEvaluationId())
                     .orElseThrow(() -> new RuntimeException("Evaluation not found"));
            calificationRegisterEntity.setEvaluation(evaluationEntity);

            calificationRegisterRepository.save(calificationRegisterEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CalificationRegisterResponseDTO> getCalificationRegisterByLevel(String level) {
        return calificationRegisterRepository.findByLevel(level).stream()
                .map(CalificationRegisterMapper::toModel)
                .map(CalificationRegisterMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CalificationRegisterResponseDTO> getCalificationRegisterByEvaluation(Long id) {
        return calificationRegisterRepository.findByEvaluationId(id).stream()
                .map(CalificationRegisterMapper::toModel)
                .map(CalificationRegisterMapper::toDTO)
                .collect(Collectors.toList());
    }
}

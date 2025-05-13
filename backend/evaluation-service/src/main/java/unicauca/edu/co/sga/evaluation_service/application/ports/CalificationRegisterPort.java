package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.CalificationRegisterRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CalificationRegisterResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CalificationRegisterPort {
    List<CalificationRegisterResponseDTO> getCalificationRegisters();
    Optional<CalificationRegisterResponseDTO> getCalificationRegisterById(Long id);
    CalificationRegisterResponseDTO saveCalificationRegister(CalificationRegisterRequestDTO calificationRegister);
    boolean deleteCalificationRegister(Long id);
    boolean updateCalificationRegister(Long id, CalificationRegisterRequestDTO calificationRegister);
    List<CalificationRegisterResponseDTO> getCalificationRegisterByLevel(String level);
    List<CalificationRegisterResponseDTO> getCalificationRegisterByEvaluation(Long id);
}

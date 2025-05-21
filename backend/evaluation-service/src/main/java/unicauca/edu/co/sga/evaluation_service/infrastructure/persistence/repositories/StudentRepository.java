package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.StudentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    List<StudentEntity> findByNameContainingIgnoreCase(String name);
    Optional<StudentEntity> findByIdentification(Long identification);
    List<StudentEntity> findByIdentificationType(GeneralEnums.identificationType type);
}

package unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.SubjectEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    Optional<SubjectEntity> findByNameContainingIgnoreCase(String name);
    List<SubjectEntity> findByStatus(GeneralEnums.status status);
}

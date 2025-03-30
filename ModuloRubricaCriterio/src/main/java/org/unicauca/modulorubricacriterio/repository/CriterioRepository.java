package org.unicauca.modulorubricacriterio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unicauca.modulorubricacriterio.model.CriterioEntity;

@Repository
public interface CriterioRepository extends JpaRepository<CriterioEntity, Long> {
}

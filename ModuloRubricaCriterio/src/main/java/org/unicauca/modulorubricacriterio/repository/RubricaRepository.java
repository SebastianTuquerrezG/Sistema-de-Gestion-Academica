package org.unicauca.modulorubricacriterio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unicauca.modulorubricacriterio.model.RubricaEntity;

@Repository
public interface RubricaRepository extends JpaRepository<RubricaEntity,Long> {
}

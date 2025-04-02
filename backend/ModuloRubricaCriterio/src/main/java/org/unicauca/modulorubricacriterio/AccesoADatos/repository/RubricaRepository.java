package org.unicauca.modulorubricacriterio.AccesoADatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unicauca.modulorubricacriterio.AccesoADatos.model.RubricaEntity;

@Repository
public interface RubricaRepository extends JpaRepository<RubricaEntity,Long> {

}

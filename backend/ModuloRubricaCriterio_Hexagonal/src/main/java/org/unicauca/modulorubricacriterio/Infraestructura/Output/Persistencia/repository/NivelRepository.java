package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.NivelEntity;

@Repository
public interface NivelRepository extends JpaRepository<NivelEntity,Long> {
}

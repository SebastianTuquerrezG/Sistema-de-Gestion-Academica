package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RAEntity;

public interface RARepository extends JpaRepository<RAEntity, Long> {
}

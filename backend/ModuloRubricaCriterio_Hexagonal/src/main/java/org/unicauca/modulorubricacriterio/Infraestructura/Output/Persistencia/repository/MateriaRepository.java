package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.MateriaEntity;

public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
}

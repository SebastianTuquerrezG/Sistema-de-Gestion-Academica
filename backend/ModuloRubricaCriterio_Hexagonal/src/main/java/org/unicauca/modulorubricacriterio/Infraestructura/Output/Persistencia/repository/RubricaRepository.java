package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RubricaEntity;

@Repository
public interface RubricaRepository extends JpaRepository<RubricaEntity,Long> {
    @Query(value = "SELECT s.name FROM subject s INNER JOIN rubrica r ON r.subject_id = s.subject_id WHERE r.id_rubrica = :idRubrica", nativeQuery = true)
    String findSubjectNameByRubricaId(@Param("idRubrica") Long idRubrica);

    @Query(value = "SELECT COUNT(*) FROM Rubrica r WHERE r.nombre_Rubrica = :nombreRubrica",nativeQuery = true)
    Integer existeRubricaConNombre(@Param("nombreRubrica") String nombreRubrica);
}

package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RubricaEntity;

import java.util.List;

@Repository
public interface RubricaRepository extends JpaRepository<RubricaEntity,Long> {
    /**
     * Consulta el nombre de una materia asociada a una rúbrica
     * @param idRubrica
     * @return Nombre de una materia asociada a una rúbrica
     */
    @Query(value = "SELECT s.name FROM subject s INNER JOIN rubrica r ON r.subject_id = s.subject_id WHERE r.id_rubrica = :idRubrica", nativeQuery = true)
    String findSubjectNameByRubricaId(@Param("idRubrica") Long idRubrica);

    /**
     * Verifica si existe una rúbrica con el nombre dado
     * @param nombreRubrica
     * @return Número de rúbricas con el nombre dado
     */
    @Query(value = "SELECT COUNT(*) FROM rubrica r WHERE r.nombre_Rubrica = :nombreRubrica",nativeQuery = true)
    Integer existeRubricaConNombre(@Param("nombreRubrica") String nombreRubrica);

    List<RubricaEntity> findAllBySubjectId(Long idMateria); /*<!Consulta una lista de rúbricas por materia*/
}

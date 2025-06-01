package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

   /* @Query("""
    SELECT s.id FROM StudentEntity s
    WHERE
        FUNCTION('replace', FUNCTION('replace', FUNCTION('unaccent', LOWER(TRIM(s.name))), ' ', ''), ' ', '') =
        FUNCTION('replace', FUNCTION('replace', FUNCTION('unaccent', LOWER(TRIM(:name))), ' ', ''), ' ', '')
""")
    List<Long> findIdsByName(@Param("name") String name);*/

    @Query("""
    SELECT s.id FROM StudentEntity s 
    WHERE 
        FUNCTION('replace', FUNCTION('replace', LOWER(TRIM(s.name)), ' ', ''), ' ', '') = 
        FUNCTION('replace', FUNCTION('replace', LOWER(TRIM(:name)), ' ', ''), ' ', '')
""")
    List<Long> findIdsByName(@Param("name") String name);


}

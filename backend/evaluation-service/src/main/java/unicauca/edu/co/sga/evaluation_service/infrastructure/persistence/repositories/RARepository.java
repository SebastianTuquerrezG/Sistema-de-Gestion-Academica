package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RAEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RARepository extends JpaRepository<RAEntity, Long> {
    List<RAEntity> findByNameContainingIgnoreCase(String name);
    @EntityGraph(attributePaths = {"course"})
    @Query("SELECT ra FROM RAEntity ra")  // Nombre libre del método
    List<RAEntity> findAllWithCourses();
    @EntityGraph(attributePaths = {"course", "rubric"})
    Optional<RAEntity> findById(Long id);
}

package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.stats;

public interface CriterioHistogramaRepository {
    Long getIdCriterio();
    String getDescripcionCriterio();
    String getNivel();
    Long getCantidad();
}

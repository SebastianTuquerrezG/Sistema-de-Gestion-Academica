package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Nivel")
public class PerformanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long idNivel;

//    @Column(nullable = false, length = 200)
//    private String name;

    @Column(nullable = false, length = 300)
    private String nivelDescripcion;

    @Column(nullable = false)
    private String rangoNota;

    /*@OneToMany(mappedBy = "performanceLevel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CriteriaEntity> criteria;*/

    @ManyToOne
    @JoinColumn(name = "id_criterio")
    @JsonIgnore
    private CriteriaEntity criterio;
}

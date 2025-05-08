package unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.RubricEntity;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Criterio")
public class CriteriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_criterio", nullable = false, unique = true, updatable = false)
    private Long idCriterio;

    @Column(nullable = false, length = 500)
    private String crfDescripcion;

    @Column(nullable = false)
    private Float crfPorcentaje;

    @Column
    private Float crfNota;

    @Column(length = 500)
    private String crfComentario;

    /*@OneToMany(mappedBy = "criteria", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RubricEntity> rubric;*/

    @ManyToOne
    @JoinColumn(name = "id_rubrica")
    @JsonBackReference
    @JsonProperty("rubric")
    private RubricEntity rubric;

    /*@ManyToOne
    @JoinColumn(name = "performance_level_id", nullable = false, foreignKey = @ForeignKey(name = "fk_performance"))
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PerformanceEntity performanceLevel;*/

    @OneToMany(mappedBy = "criterio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PerformanceEntity> niveles;


}

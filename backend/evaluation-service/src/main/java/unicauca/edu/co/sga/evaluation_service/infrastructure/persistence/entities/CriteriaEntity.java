package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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
    @JsonBackReference("rubric-criteria")
    @JsonProperty("rubric")
    private RubricEntity rubric;

    /*@ManyToOne
    @JoinColumn(name = "performance_level_id", nullable = false, foreignKey = @ForeignKey(name = "fk_performance"))
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PerformanceEntity performanceLevel;*/

    @OneToMany(mappedBy = "criterio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PerformanceEntity> niveles;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CriteriaEntity that = (CriteriaEntity) o;
        return getIdCriterio() != null && Objects.equals(getIdCriterio(), that.getIdCriterio());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "calification_register")
public class CalificationRegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false, name = "calification_id")
    private Long id;

    @Column(nullable = false)
    private Double calification;

    @Column(nullable = false, length = 300)
    private String message;

    @Column(nullable = false, length = 100)
    private String level; //As an example: "Nivel 1", "Nivel 2"

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false, foreignKey = @ForeignKey(name = "fk_evaluation"))
    @JsonBackReference("evaluation-calification")
    private EvaluationEntity evaluation;

    @ManyToOne
    @JoinColumn(name = "id_criterio", nullable = false, foreignKey = @ForeignKey(name = "fk_criteria"))
    @JsonBackReference("criteria-calification")
    private CriteriaEntity criterio;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CalificationRegisterEntity that = (CalificationRegisterEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
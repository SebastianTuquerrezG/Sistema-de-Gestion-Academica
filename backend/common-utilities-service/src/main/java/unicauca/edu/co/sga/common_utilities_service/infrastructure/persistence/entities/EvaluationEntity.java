package unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.EvaluationStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "evaluation")
public class EvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enroll_id", nullable = false, foreignKey = @ForeignKey(name = "fk_enroll"))
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EnrollEntity enroll;

    @ManyToOne
    @JoinColumn(name = "id_rubrica", nullable = false, foreignKey = @ForeignKey(name = "fk_rubrica"))
    @JsonBackReference("rubric-evaluation")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RubricEntity rubric;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("evaluation-calification")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CalificationRegisterEntity> califications;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "evidence_url", length = 500)
    private String evidenceUrl;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        EvaluationEntity that = (EvaluationEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import unicauca.edu.co.sga.evaluation_service.domain.enums.EvaluationStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Data
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
    @JsonProperty("enroll")
    private EnrollEntity enroll;

    @ManyToOne
    @JoinColumn(name = "rubric_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rubric"))
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonProperty("rubric")
    private RubricEntity rubric;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
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
}

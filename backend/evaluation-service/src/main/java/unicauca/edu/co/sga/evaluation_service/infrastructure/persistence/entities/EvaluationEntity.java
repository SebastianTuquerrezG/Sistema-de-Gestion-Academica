package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
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
    @JsonBackReference
    private EnrollEntity enroll;

    @ManyToOne
    @JoinColumn(name = "rubric_id", nullable = false, foreignKey = @ForeignKey(name = "fk_rubric"))
    @JsonBackReference
    private RubricEntity rubric;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CalificationRegisterEntity> califications;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updated_at;
}

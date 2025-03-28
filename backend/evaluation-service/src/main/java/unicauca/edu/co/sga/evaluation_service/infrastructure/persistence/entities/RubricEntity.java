package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rubric")
public class RubricEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rubric_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 300)
    private String study_objective;

    @Column(nullable = false, length = 300)
    private String competence; // This is a entity that we don't know, Take care of this shit bro

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false, foreignKey = @ForeignKey(name = "fk_subject}"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "ra_id", nullable = false, foreignKey = @ForeignKey(name = "fk_learning_results"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private RAEntity ra;

    @ManyToOne
    @JoinColumn(name = "criteria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_criteria"))
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CriteriaEntity criteria;

    @OneToMany(mappedBy = "rubric", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EvaluationEntity> evaluation;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneralEnums.status status;
}

package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Rubrica")
public class RubricEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rubrica", unique = true, nullable = false, updatable = false)
    private Long idRubrica;

    @Column(nullable = false, length = 100)
    private String nombreRubrica;

    @Column(nullable = false, length = 300)
    private String objetivoEstudio;

    @Column(nullable = false)
    private int notaRubrica;

//    @Column(nullable = false, length = 300)
//    private String competence; // This is a entity that we don't know, Take care of this shit bro

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
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

    /*@ManyToOne
    @JoinColumn(name = "criteria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_criteria"))
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CriteriaEntity criteria;*/

    @OneToMany(mappedBy = "rubric", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CriteriaEntity> criterios;

    @OneToMany(mappedBy = "rubric", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EvaluationEntity> evaluation;

//    @Column(updatable = false)
//    @CreationTimestamp
//    @Temporal(TemporalType.DATE)
//    private Date created_at;
//
//    @UpdateTimestamp
//    @Temporal(TemporalType.DATE)
//    private Date updated_at;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneralEnums.status estado;
}

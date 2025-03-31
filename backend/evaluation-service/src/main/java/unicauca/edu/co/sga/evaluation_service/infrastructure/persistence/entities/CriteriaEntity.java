package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "criteria")
public class CriteriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "criteria_id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(nullable = false)
    private float percentage;

    /*@OneToMany(mappedBy = "criteria", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RubricEntity> rubric;*/

    @ManyToOne
    @JoinColumn(name = "rubric_id")
    @JsonBackReference
    private RubricEntity rubric;

    /*@ManyToOne
    @JoinColumn(name = "performance_level_id", nullable = false, foreignKey = @ForeignKey(name = "fk_performance"))
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PerformanceEntity performanceLevel;*/

    @OneToMany(mappedBy = "criterio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PerformanceEntity> levels;


}

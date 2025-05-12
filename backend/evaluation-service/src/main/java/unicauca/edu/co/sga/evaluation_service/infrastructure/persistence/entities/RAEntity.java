package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "learning_results")
public class RAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ra_id", nullable = false, unique = true, updatable = false)
    private Long id;

    @OneToMany(mappedBy = "ra", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("ra-rubric")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RubricEntity> rubric;

    @OneToMany(mappedBy = "ra", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("ra-course")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CourseEntity> course;
}

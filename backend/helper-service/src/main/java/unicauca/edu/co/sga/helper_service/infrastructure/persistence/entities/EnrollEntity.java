package unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.StudentEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "enroll")
public class EnrollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enroll_id", nullable = false, unique = true, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "fk_course"))
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "fk_student"))
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StudentEntity student;

    //@OneToMany(mappedBy = "enroll", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    //@ToString.Exclude
    //@EqualsAndHashCode.Exclude
    //private Set<EvaluationEntity> evaluation;

    @Column(nullable = false, length = 100)
    private String semester;
}

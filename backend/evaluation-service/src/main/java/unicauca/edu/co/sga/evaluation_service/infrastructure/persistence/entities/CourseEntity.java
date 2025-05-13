package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_teacher",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TeacherEntity> teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false, foreignKey = @ForeignKey(name = "fk_subject"))
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "ra_id", nullable = false, foreignKey = @ForeignKey(name = "fk_ra"))
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RAEntity ra;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EnrollEntity> enroll;
}

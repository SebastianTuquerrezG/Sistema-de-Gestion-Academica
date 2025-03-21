package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
@Data
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private int id;

    @ManyToMany
    @JoinTable(name = "course_teacher",
            joinColumns = @JoinColumn(name = "courseID"),
            inverseJoinColumns = @JoinColumn(name = "teacherID"))
    @JsonBackReference
    private Set<Teacher> teacher;

    @ManyToOne
    @JoinColumn(name = "subjectID", nullable = false, foreignKey = @ForeignKey(name = "fk_subject"))
    @JsonBackReference
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "raID", nullable = false, foreignKey = @ForeignKey(name = "fk_ra"))
    @JsonBackReference
    private RA ra;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Enroll> enroll;


}

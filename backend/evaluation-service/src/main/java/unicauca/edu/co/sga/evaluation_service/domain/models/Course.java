package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "course")
@Data
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private int id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherID", nullable = false, foreignKey = @ForeignKey(name = "fk_teacher"))
    @JsonBackReference
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjectID", nullable = false, foreignKey = @ForeignKey(name = "fk_subject"))
    private Subject subject;


}

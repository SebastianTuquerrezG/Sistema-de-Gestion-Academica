package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.domain.enums.TeacherEnums;

import java.util.Set;

@Entity
@Table(name = "teacher")
@Data
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long identification;

    @Column(nullable = false)
    private String degree;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneralEnums.identificationType identificationType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneralEnums.status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeacherEnums.teacher_type teacherType;

    @ManyToMany(mappedBy = "teacher")
    @JsonBackReference
    private Set<Course> course;
}

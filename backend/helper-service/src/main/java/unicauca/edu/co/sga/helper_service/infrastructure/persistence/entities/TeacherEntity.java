package unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import unicauca.edu.co.sga.helper_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.helper_service.domain.enums.TeacherEnums;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.CourseEntity;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "teacher")
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long identification;

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
    private TeacherEnums teacherType;

    @ManyToMany(mappedBy = "teacher")
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CourseEntity> course;
}

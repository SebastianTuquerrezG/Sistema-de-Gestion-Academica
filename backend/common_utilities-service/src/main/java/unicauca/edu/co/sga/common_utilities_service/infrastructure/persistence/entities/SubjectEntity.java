package unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "subject")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false, length = 300)
    private String objectives;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneralEnums.status status;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(updatable = true)
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("subject-course")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CourseEntity> course;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("subject-rubric")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RubricEntity> rubric;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SubjectEntity that = (SubjectEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

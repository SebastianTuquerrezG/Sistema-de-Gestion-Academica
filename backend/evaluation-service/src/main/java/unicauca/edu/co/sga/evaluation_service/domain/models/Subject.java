package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "subject")
@Data
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int credits;

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
    @JsonBackReference
    private Set<Course> course;
}

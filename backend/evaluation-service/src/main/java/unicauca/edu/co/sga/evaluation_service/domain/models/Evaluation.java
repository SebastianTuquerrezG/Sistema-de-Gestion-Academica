package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "evaluation")
@Data
@Builder
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "enrollID", nullable = false, foreignKey = @ForeignKey(name = "fk_enroll"))
    @JsonBackReference
    private Enroll enroll;

    @ManyToOne
    @JoinColumn(name = "rubricID", nullable = false, foreignKey = @ForeignKey(name = "fk_rubric"))
    @JsonBackReference
    private Rubric rubric;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<CalificationsRegister> calification;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updated_at;

}

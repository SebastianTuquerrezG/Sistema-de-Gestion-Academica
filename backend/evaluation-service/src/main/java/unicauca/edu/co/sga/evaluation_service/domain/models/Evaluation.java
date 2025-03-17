package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "evaluation")
@Data
@Builder
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private int id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "enrollID", nullable = false, foreignKey = @ForeignKey(name = "fk_enroll"))
    @JsonBackReference
    private Enroll enroll;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rubricID", nullable = false, foreignKey = @ForeignKey(name = "fk_rubric"))
    @JsonBackReference
    private Rubric rubric;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(updatable = true)
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updated_at;
}

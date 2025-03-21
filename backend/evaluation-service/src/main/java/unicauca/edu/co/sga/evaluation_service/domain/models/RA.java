package unicauca.edu.co.sga.evaluation_service.domain.models;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "learningResult")
@Data
@Builder
public class RA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @OneToMany(mappedBy = "ra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> course;
}

package unicauca.edu.co.sga.evaluation_service.domain.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "rubric")
@Data
@Builder
public class Rubric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "rubric", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Evaluation> evaluation;
}

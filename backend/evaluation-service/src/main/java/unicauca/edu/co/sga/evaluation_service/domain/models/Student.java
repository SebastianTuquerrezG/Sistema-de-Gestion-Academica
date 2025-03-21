package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import java.util.Set;

@Entity
@Table(name = "student")
@Data
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long indentification;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneralEnums.identificationType identification_type;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Enroll> enroll;
}

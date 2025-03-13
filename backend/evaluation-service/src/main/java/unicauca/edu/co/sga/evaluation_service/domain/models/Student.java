package unicauca.edu.co.sga.evaluation_service.domain.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

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
}

package org.unicauca.modulorubricacriterio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Nivel")
@Data
public class NivelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNivel;

    @ManyToOne
    @JoinColumn(name = "idCriterio")
    @JsonBackReference
    private CriterioEntity criterio;

    private String nivelDescripcion;
    private String rangoNota;
}

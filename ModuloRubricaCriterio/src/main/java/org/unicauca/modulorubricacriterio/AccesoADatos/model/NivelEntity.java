package org.unicauca.modulorubricacriterio.AccesoADatos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Nivel")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public NivelEntity(CriterioEntity criterio1, String nDescripcion, String rangoNota) {
        this.criterio = criterio1;
        this.nivelDescripcion = nDescripcion;
        this.rangoNota = rangoNota;
    }

    
}

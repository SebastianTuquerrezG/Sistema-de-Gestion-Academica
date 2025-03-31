package org.unicauca.modulorubricacriterio.AccesoADatos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Criterio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriterioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCriterio;

    @ManyToOne
    @JoinColumn(name = "idRubrica")
    @JsonBackReference
    private RubricaEntity rubrica;

    @Column(length = 500)
    private String crfDescripcion;

    private Float crfPorcentaje;
    private Float crfNota;

    @Column(length = 500)
    private String crfComentario;

    @OneToMany(mappedBy = "criterio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<NivelEntity> niveles;
    



}

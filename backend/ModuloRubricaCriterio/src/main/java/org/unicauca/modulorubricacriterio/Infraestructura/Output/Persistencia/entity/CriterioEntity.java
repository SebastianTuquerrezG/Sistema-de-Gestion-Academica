package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "id_criterio", nullable = false, unique = true, updatable = false)
    private Long idCriterio;

    @Column(nullable = false, length = 500)
    private String crfDescripcion;

    @Column(nullable = false)
    private Integer crfPorcentaje;

    @Column
    private Float crfNota;

    @Column(length = 500)
    private String crfComentario;

    @OneToMany(mappedBy = "criterio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<NivelEntity> niveles;

    @ManyToOne
    @JoinColumn(name = "id_rubrica")
    @JsonBackReference("rubric-criteria")
    @JsonProperty("rubric")
    private RubricaEntity rubrica;

    public CriterioEntity(RubricaEntity rubrica, String descripcion, Integer porcentaje, float nota, String comentario, List<NivelEntity> niveles) {
        this.rubrica = rubrica;
        this.crfDescripcion = descripcion;
        this.crfPorcentaje = porcentaje;
        this.crfNota = nota;
        this.crfComentario = comentario;
        this.niveles = niveles;
    }
}

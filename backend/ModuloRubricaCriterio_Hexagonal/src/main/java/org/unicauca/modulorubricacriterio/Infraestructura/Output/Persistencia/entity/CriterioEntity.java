package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity;

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
    private Long idCriterio;

    @ManyToOne
    @JoinColumn(name = "id_rubrica")
    private RubricaEntity rubrica;

    @Column(length = 500)
    private String crfDescripcion;
    private Float crfPorcentaje;
    private Float crfNota;

    @Column(length = 500)
    private String crfComentario;

    @OneToMany(mappedBy = "criterio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<NivelEntity> niveles;

    public CriterioEntity(RubricaEntity rubrica, String desccripcion, float porcentaje, float nota, String comentario, List<NivelEntity> niveles) {
        this.rubrica = rubrica;
        this.crfDescripcion = desccripcion;
        this.crfPorcentaje = porcentaje;
        this.crfNota = nota;
        this.crfComentario = comentario;
        this.niveles = niveles;
    }
    



}

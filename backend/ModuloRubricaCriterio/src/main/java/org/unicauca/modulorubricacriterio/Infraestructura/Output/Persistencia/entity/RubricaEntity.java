package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;

@Getter
@Setter
@Entity
@Table(name="Rubrica")
@AllArgsConstructor
@NoArgsConstructor
public class RubricaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRubrica;
    private String nombreRubrica;
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false, foreignKey = @ForeignKey(name = "fk_subject"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private MateriaEntity subject;
    private int notaRubrica;
    private String objetivoEstudio;
    @OneToMany(mappedBy = "rubrica", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CriterioEntity>criterios;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadosEnum estadoRubrica;

    @ManyToOne
    @JoinColumn(name = "ra_id", nullable = false, foreignKey = @ForeignKey(name = "fk_learning_results"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private RAEntity ra;

    public RubricaEntity(String nameRubrica, MateriaEntity materia, int nota, String objetivoDeEstudio, List<CriterioEntity> object, EstadosEnum activo, RAEntity ra) {
        this.nombreRubrica = nameRubrica;
        this.subject = materia;
        this.notaRubrica = nota;
        this.objetivoEstudio = objetivoDeEstudio;
        this.criterios = object;
        this.estadoRubrica = activo;
        this.ra = ra;
    }

}
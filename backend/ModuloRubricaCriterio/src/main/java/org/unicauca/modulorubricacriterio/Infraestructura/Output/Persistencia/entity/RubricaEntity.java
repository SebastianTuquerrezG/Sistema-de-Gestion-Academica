package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Builder
@ToString
public class RubricaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rubrica", unique = true, nullable = false, updatable = false)
    private Long idRubrica;

    @Column(nullable = false, length = 100)
    private String nombreRubrica;

    @Column(nullable = false, length = 300)
    private String objetivoEstudio;

    @Column(nullable = false)
    private int notaRubrica;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false, foreignKey = @ForeignKey(name = "fk_subject"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference("subject-rubric")
    private MateriaEntity subject;

    @OneToMany(mappedBy = "rubrica", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("rubric-criteria")
    @ToString.Exclude
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
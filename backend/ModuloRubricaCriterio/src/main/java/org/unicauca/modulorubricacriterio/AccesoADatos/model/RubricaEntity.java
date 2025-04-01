package org.unicauca.modulorubricacriterio.AccesoADatos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.unicauca.modulorubricacriterio.Fachada.validacionEstados.EstadosEnum;

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
    private String materia;
    private int notaRubrica;
    private String objetivoEstudio;
    @OneToMany(mappedBy = "rubrica", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<CriterioEntity>criterios;
    private EstadosEnum estadoRubrica;

    public RubricaEntity(String nameRubrica, String materia, int nota, String objetivoDeEstudio, List<CriterioEntity> object, EstadosEnum activo) {
        this.nombreRubrica = nameRubrica;
        this.materia = materia;
        this.notaRubrica = nota;
        this.objetivoEstudio = objetivoDeEstudio;
        this.criterios = object;
        this.estadoRubrica = activo;
    }

}

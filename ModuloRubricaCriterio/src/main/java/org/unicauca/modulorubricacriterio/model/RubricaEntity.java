package org.unicauca.modulorubricacriterio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private int notaRubrica;
    private String objetivoEstudio;
    @OneToMany(mappedBy = "rubrica", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<CriterioEntity>criterios;


}

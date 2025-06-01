package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "subject")
public class MateriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false, length = 300)
    private String objectives;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadosEnum status;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(updatable = true)
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    public MateriaEntity(String name, Integer credits, String objectives, EstadosEnum status) {
        this.name = name;
        this.credits = credits;
        this.objectives = objectives;
        this.status = status;
    }
}

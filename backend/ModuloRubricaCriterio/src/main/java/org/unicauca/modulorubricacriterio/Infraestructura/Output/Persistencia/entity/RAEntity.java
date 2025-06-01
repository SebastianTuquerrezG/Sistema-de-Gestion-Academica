package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "learning_results")
public class RAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ra_id", nullable = false, unique = true, updatable = false)
    private Long id;
}

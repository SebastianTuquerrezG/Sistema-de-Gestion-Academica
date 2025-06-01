package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;


@Entity
@Table(name = "Nivel")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNivel;

    @ManyToOne
    @JoinColumn(name = "id_criterio")
    @JsonIgnore
    private CriterioEntity criterio;

    @Column(nullable = false, length = 300)
    private String nivelDescripcion;

    @Column(nullable = false)
    private String rangoNota;

    public NivelEntity(CriterioEntity criterio1, String nDescripcion, String rangoNota) {
        this.criterio = criterio1;
        this.nivelDescripcion = nDescripcion;
        this.rangoNota = rangoNota;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        NivelEntity that = (NivelEntity) o;
        return getIdNivel() != null && Objects.equals(getIdNivel(), that.getIdNivel());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

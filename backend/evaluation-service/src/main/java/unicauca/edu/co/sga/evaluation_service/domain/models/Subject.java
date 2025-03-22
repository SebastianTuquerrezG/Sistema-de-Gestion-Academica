package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {
    private Long id;
    private String name;
    private Integer credits;
    private String objectives;
    private GeneralEnums.status status;
    private Date created_at;
    private Date updated_at;
    private Set<Long> course;
}

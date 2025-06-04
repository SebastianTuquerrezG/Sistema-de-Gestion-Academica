package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
public class NivelDesempenoDTO {
    private String nivel;
    private Long cantidad;

    public NivelDesempenoDTO(String nivel, Long cantidad) {
        this.nivel = nivel;
        this.cantidad = cantidad;
    }
}

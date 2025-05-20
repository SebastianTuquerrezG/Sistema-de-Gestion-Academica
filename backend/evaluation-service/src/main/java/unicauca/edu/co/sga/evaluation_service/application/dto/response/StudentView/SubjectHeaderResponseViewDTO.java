package unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectHeaderResponseViewDTO {

    private String nameSubject;
    private String nameTeacher;
    private String period;

}

package org.unicauca.modulorubricacriterio.adapters.messaging;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionCriterioAdapter;
import org.unicauca.modulorubricacriterio.Infraestructura.Configuration.rabbit.RabbitMQConfig;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion.CriterioDTOPeticion;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta.CriterioDTORespuesta;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.conectoresBD.ConectorBDCriterioAdapter;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.conectoresBD.ConectorBDNivelAdapter;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.conectoresBD.ConectorBDRubricaAdapter;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    private final ConectorBDRubricaAdapter gestionRubricasAdapter;
    private final ConectorBDCriterioAdapter gestionCriteriosAdapter;
    private final ConectorBDNivelAdapter gestionNivelAdapter;

    /*
    @RabbitListener(queues = RabbitMQConfig.QUEUE_SUBJECT)
    public void readSubjectData(@Payload SubjectRequestDTO message) {
        SubjectResponseDTO subjectResponseDTO = subjectService.saveSubject(message);
        log.info("Message from SUBJECT microservice (SUBJECT ENTITY): {}", message);
        if(subjectResponseDTO != null){
            log.info("Database updated with new SUBJECT data. Data {}", subjectResponseDTO);
        }else{
            log.info("There are some errors with the database (SUBJECT).");
        }
    }

     */

}

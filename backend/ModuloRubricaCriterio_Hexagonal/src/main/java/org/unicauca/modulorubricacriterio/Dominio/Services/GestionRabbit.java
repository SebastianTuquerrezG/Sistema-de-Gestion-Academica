package org.unicauca.modulorubricacriterio.Dominio.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.Aplicacion.Input.IRabbitPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Infraestructura.Configuration.rabbit.RabbitMQConfig;
import org.unicauca.modulorubricacriterio.adapters.messaging.RabbitMQProducer;

@Service
@Transactional
@RequiredArgsConstructor
public class GestionRabbit implements IRabbitPort {

    private final RabbitMQProducer rabbitMQProducer;
    @Override
    public void sendRubric(Rubrica rubrica) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_RUBRIC, rubrica);
    }

    @Override
    public void updateRubric(Rubrica rubrica) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_RUBRIC, rubrica);
    }

    @Override
    public void deleteRubric(Rubrica rubrica) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_RUBRIC, rubrica);
    }




    @Override
    public void sendCriterio(Criterio criterio) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_CRITERIA, criterio);
    }

    @Override
    public void updateCriterio(Criterio criterio) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_CRITERIA, criterio);
    }

    @Override
    public void deleteCriterio(Criterio criterio) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_CRITERIA, criterio);
    }

    @Override
    public void sendNivel(Nivel nivel) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_PERFORMANCE, nivel);

    }

    @Override
    public void updateNivel(Nivel nivel) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_PERFORMANCE, nivel);

    }

    @Override
    public void deleteNivel(Nivel nivel) {
        rabbitMQProducer.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_PERFORMANCE, nivel);

    }
}

package org.unicauca.modulorubricacriterio.Infraestructura.Configuration.rabbit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "messaging_exchange";

    // Variables for rabbit of rubric module
    // TODO: Create the different use of these variables into the other microserver.
    public static final String ROUTING_KEY_RUBRIC = "rubric_routing_key";
    public static final String ROUTING_KEY_UPDATE_RUBRIC = "rubric_update_routing_key";
    public static final String ROUTING_KEY_DELETE_RUBRIC = "rubric_delete_routing_key";

    public static final String ROUTING_KEY_PERFORMANCE = "performance_routing_key";
    public static final String ROUTING_KEY_UPDATE_PERFORMANCE = "performance_update_routing_key";
    public static final String ROUTING_KEY_DELETE_PERFORMANCE = "performance_delete_routing_key";

    public static final String ROUTING_KEY_CRITERIA = "criteria_routing_key";
    public static final String ROUTING_KEY_UPDATE_CRITERIA = "criteria_update_routing_key";
    public static final String ROUTING_KEY_DELETE_CRITERIA = "criteria_delete_routing_key";

    // Serialization for JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        DefaultClassMapper classMapper = new DefaultClassMapper(){
            @Override
            public void fromClass(Class<?> clazz, MessageProperties properties){
                properties.setHeader(getClassIdFieldName(), clazz.getSimpleName());
            }
        };

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        addDTOMapping(idClassMapping);

        classMapper.setIdClassMapping(idClassMapping);
        classMapper.setTrustedPackages("*");

        converter.setClassMapper(classMapper);

        return converter;
    }

    /**
     * Método para agregar mapeos de DTOs
     */
    private void addDTOMapping(Map<String, Class<?>> idClassMapping){
        idClassMapping.put("Criterio", Criterio.class);
        idClassMapping.put("Nivel", Nivel.class);
        idClassMapping.put("Rubrica", Rubrica.class);
    }

    // This method is in both Publisher and Consumer
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connection){
        final var template = new RabbitTemplate(connection);
        template.setMessageConverter(messageConverter());
        return template;
    }

    // This method is in both Publisher and Consumer
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

//    @Bean
//    public Binding bindingRa(Queue queueRa, TopicExchange exchange){
//        return BindingBuilder.bind(queueRa).to(exchange).with(ROUTING_KEY_RA);
//    }
}

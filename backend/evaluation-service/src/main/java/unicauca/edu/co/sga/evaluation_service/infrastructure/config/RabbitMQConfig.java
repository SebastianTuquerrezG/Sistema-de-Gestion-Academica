package unicauca.edu.co.sga.evaluation_service.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "messaging_exchange";

    // Variables for rabbit publisher
    public static final String ROUTING_KEY_ENROLL = "enroll_routing_key";
    public static final String ROUTING_KEY_EVALUATION = "evaluation_routing_key";


    // Serialization for JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO", EnrollRequestDTO.class);
        idClassMapping.put("unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity", EvaluationEntity.class);

        classMapper.setIdClassMapping(idClassMapping);
        converter.setClassMapper(classMapper);

        return converter;
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
}

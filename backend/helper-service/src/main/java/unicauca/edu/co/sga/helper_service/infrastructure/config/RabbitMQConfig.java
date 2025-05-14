package unicauca.edu.co.sga.helper_service.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unicauca.edu.co.sga.helper_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EvaluationEntity;

import javax.sound.midi.Receiver;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "messaging_exchange";

    // Variables for rabbit publisher
    public static final String ROUTING_KEY_ENROLL = "enroll_routing_key";
    public static final String ROUTING_KEY_EVALUATION = "evaluation_routing_key";

    // Variables for rabbit consumer
    public static final String QUEUE_ENROLL = "enroll_queue";
    public static final String QUEUE_EVALUATION = "evaluation_queue";


    // Variables for rabbit publisher  of Helper_service
    public static final String ROUTING_KEY_TEACHER = "teacher_routing_key";
    public static final String ROUTING_KEY_SUBJECT = "subject_routing_key";
    public static final String ROUTING_KEY_COURSE = "course_routing_key";
    public static final String ROUTING_KEY_STUDENT = "student_routing_key";
    public static final String ROUTING_KEY_RA = "RA_routing_key";


    // Serialization for JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();

        Map<String, Class<?>> idClassMapping = new HashMap<>();
//        idClassMapping.put("unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO", EnrollRequestDTO.class);
//        idClassMapping.put("unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity", EvaluationEntity.class);

        idClassMapping.put("EnrollRequestDTO", EnrollRequestDTO.class);

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

    // For Consumer
    @Bean
    public Queue queueEnroll(){
        return new Queue(QUEUE_ENROLL, false);
    }

    @Bean
    public Queue queueEvaluation(){
        return new Queue(QUEUE_EVALUATION, false);
    }

    // This method is in both Publisher and Consumer
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }


    // For Consumer
    @Bean
    public Binding bindingEnroll(Queue queueEnroll, TopicExchange exchange){
        return BindingBuilder.bind(queueEnroll).to(exchange).with(ROUTING_KEY_ENROLL);
    }

    @Bean
    public Binding bindingEvaluation(Queue queueEvaluation, TopicExchange exchange){
        return BindingBuilder.bind(queueEvaluation).to(exchange).with(ROUTING_KEY_EVALUATION);
    }
}

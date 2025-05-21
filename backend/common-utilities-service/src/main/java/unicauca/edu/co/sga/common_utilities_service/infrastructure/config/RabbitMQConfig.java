package unicauca.edu.co.sga.common_utilities_service.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.EvaluationRequestDTO;

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


    // Variables for rabbit publisher  of Common_utilities_service
    public static final String ROUTING_KEY_TEACHER = "teacher_routing_key";
    public static final String ROUTING_KEY_SUBJECT = "subject_routing_key";
    public static final String ROUTING_KEY_COURSE = "course_routing_key";
    public static final String ROUTING_KEY_STUDENT = "student_routing_key";
    public static final String ROUTING_KEY_RA = "RA_routing_key";


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
        idClassMapping.put("EnrollRequestDTO", EnrollRequestDTO.class);
        idClassMapping.put("EvaluationRequestDTO", EvaluationRequestDTO.class);
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

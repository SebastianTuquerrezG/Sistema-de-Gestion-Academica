package unicauca.edu.co.sga.evaluation_service.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "messaging_exchange";

    // Variables for rabbit publisher
    public static final String ROUTING_KEY_ENROLL = "enroll_routing_key";
    public static final String ROUTING_KEY_EVALUATION = "evaluation_routing_key";


    // Variables for rabbit consumer of Common_utilities_service
    public static final String ROUTING_KEY_TEACHER = "teacher_routing_key";
    public static final String ROUTING_KEY_SUBJECT = "subject_routing_key";
    public static final String ROUTING_KEY_COURSE = "course_routing_key";
    public static final String ROUTING_KEY_STUDENT = "student_routing_key";
//    public static final String ROUTING_KEY_RA = "RA_routing_key";

    // Queue of Common_utilities_service
    public static final String QUEUE_TEACHER = "queue_teacher";
    public static final String QUEUE_SUBJECT = "queue_subject";
    public static final String QUEUE_COURSE = "queue_course";
    public static final String QUEUE_STUDENT = "queue_student";
//    public static final String QUEUE_RA = "queue_ra";


    // Variables for rabbit of rubric module
    // TODO: Create the different use of these variables into the other microserver.
    public static final String ROUTING_KEY_RUBRIC = "rubric_routing_key";
    public static final String ROUTING_KEY_PERFORMANCE = "performance_routing_key";
    public static final String ROUTING_KEY_CRITERIA = "criteria_routing_key";

    // Queues of rubric module
    // TODO: Implement these queues with the other microservice
    public static final String QUEUE_RUBRIC = "queue_rubric";
    public static final String QUEUE_PERFORMANCE = "queue_performance";
    public static final String QUEUE_CRITERIA = "queue_criteria";



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
        idClassMapping.put("TeacherRequestDTO", TeacherRequestDTO.class);
        idClassMapping.put("SubjectRequestDTO", SubjectRequestDTO.class);
        idClassMapping.put("CourseRequestDTO", CourseRequestDTO.class);
        idClassMapping.put("StudentRequestDTO", StudentRequestDTO.class);
//        idClassMapping.put("RARequestDTO", RARequestDTO.class);
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

    // QUEUES METHODS for Common_utilities_service
    @Bean
    public Queue queueTeacher(){
        return new Queue(QUEUE_TEACHER, false);
    }

    @Bean
    public Queue queueSubject(){
        return new Queue(QUEUE_SUBJECT, false);
    }

    @Bean
    public Queue queueCourse(){
        return new Queue(QUEUE_COURSE, false);
    }

    @Bean
    public Queue queueStudent(){
        return new Queue(QUEUE_STUDENT, false);
    }

//    @Bean
//    public Queue queueRa(){
//        return new Queue(QUEUE_RA, false);
//    }

    // Binding with Common_utilities_service
    @Bean
    public Binding bindingTeacher(Queue queueTeacher, TopicExchange exchange){
        return BindingBuilder.bind(queueTeacher).to(exchange).with(ROUTING_KEY_TEACHER);
    }

    @Bean
    public Binding bindingSubject(Queue queueSubject, TopicExchange exchange){
        return BindingBuilder.bind(queueSubject).to(exchange).with(ROUTING_KEY_SUBJECT);
    }

    @Bean
    public Binding bindingStudent(Queue queueStudent, TopicExchange exchange){
        return BindingBuilder.bind(queueStudent).to(exchange).with(ROUTING_KEY_STUDENT);
    }

    @Bean
    public Binding bindingCourse(Queue queueCourse, TopicExchange exchange){
        return BindingBuilder.bind(queueCourse).to(exchange).with(ROUTING_KEY_COURSE);
    }

//    @Bean
//    public Binding bindingRa(Queue queueRa, TopicExchange exchange){
//        return BindingBuilder.bind(queueRa).to(exchange).with(ROUTING_KEY_RA);
//    }


    // QUEUES METHODS for rubric module
    @Bean
    public Queue queueRubric(){
        return new Queue(QUEUE_RUBRIC, false);
    }

    @Bean
    public Queue queuePerformance(){
        return new Queue(QUEUE_PERFORMANCE, false);
    }

    @Bean
    public Queue queueCriteria(){
        return new Queue(QUEUE_CRITERIA, false);
    }



    // These queues are for to be used with the other microservice
    // TODO: Use this queues to get the data of the other microservice and then
    // create each instance in each table of the database.
    @Bean
    public Binding bindingRubric(Queue queueRubric, TopicExchange exchange){
        return BindingBuilder.bind(queueRubric).to(exchange).with(ROUTING_KEY_RUBRIC);
    }

    @Bean
    public Binding bindingPerformance(Queue queuePerformance, TopicExchange exchange){
        return BindingBuilder.bind(queuePerformance).to(exchange).with(ROUTING_KEY_PERFORMANCE);
    }

    @Bean
    public Binding bindingCriteria(Queue queueCriteria, TopicExchange exchange){
        return BindingBuilder.bind(queueCriteria).to(exchange).with(ROUTING_KEY_CRITERIA);
    }
}

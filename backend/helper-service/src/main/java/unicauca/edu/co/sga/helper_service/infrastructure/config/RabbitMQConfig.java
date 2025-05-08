package unicauca.edu.co.sga.helper_service.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sound.midi.Receiver;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "messaging_exchange";

    // Variables for rabbit publisher
    public static final String ROUTING_KEY_ENROLL = "enroll_routing_key";

    // Variables for rabbit consumer
    public static final String QUEUE_ENROLL = "enroll_queue";

    // Serialization for JSON
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
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
}

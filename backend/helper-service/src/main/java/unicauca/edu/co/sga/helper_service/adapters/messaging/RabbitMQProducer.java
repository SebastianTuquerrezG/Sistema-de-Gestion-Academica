package unicauca.edu.co.sga.helper_service.adapters.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.helper_service.infrastructure.config.RabbitMQConfig;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQReceive rabbitMQReceive;

    public void sendMessage(String message){
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_STUDENT, message);
        try {
            rabbitMQReceive.getLatch().await(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

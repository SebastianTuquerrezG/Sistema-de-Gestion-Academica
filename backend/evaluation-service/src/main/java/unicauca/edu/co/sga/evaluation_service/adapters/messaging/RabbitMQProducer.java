package unicauca.edu.co.sga.evaluation_service.adapters.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQReceive rabbitMQReceive;

    public void sendMessage(String exchange, String routing_key, Object message){
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(exchange, routing_key, message);
        /*try {
            boolean completed = rabbitMQReceive.getLatch().await(10000, TimeUnit.MILLISECONDS);
            if (!completed) {
                throw new RuntimeException("Timeout esperando la respuesta de RabbitMQ");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}

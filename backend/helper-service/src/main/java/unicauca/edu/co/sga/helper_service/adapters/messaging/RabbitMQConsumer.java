package unicauca.edu.co.sga.helper_service.adapters.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.helper_service.infrastructure.config.RabbitMQConfig;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_STUDENT)
    public void readStudentData(String message){
        log.info("Message received: {}", message);
    }
}

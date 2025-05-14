package unicauca.edu.co.sga.evaluation_service.adapters.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.infrastructure.config.RabbitMQConfig;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    // TODO: Create all the methods to read the message from Rubric and Helper services
}

package unicauca.edu.co.sga.helper_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.helper_service.adapters.messaging.RabbitMQProducer;
import unicauca.edu.co.sga.helper_service.domain.models.StudentRabbit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
public class MessageController {
    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping("/sendStudent")
    public String sendMessage(@RequestBody StudentRabbit message){
        rabbitMQProducer.sendMessage(message.getMessage());
        return "Message sent: " + message.getMessage();
    }
}

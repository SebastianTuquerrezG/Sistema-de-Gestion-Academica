package unicauca.edu.co.sga.helper_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unicauca.edu.co.sga.helper_service.adapters.messaging.RabbitMQProducer;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final RabbitMQProducer rabbitMQProducer;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message){
        rabbitMQProducer.sendMessage(message);
        return "Message sent: " + message;
    }
}

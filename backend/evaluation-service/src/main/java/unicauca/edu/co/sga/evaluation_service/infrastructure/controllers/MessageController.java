package unicauca.edu.co.sga.evaluation_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicauca.edu.co.sga.evaluation_service.adapters.messaging.RabbitMQProducer;
import unicauca.edu.co.sga.evaluation_service.domain.models.EnrollRabbit;


// TODO: This CONTROLLER needs to be deleting. ALREADY IS USED IN SERVICE FOLDER.

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
public class MessageController {
    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping("/sendEnroll")
    public String sendMessage(@RequestBody EnrollRabbit message){
//        rabbitMQProducer.sendEnroll(message.getMessage()); TODO Do the exchange and key with message.
        return "Message sent: " + message.getMessage();
    }
}

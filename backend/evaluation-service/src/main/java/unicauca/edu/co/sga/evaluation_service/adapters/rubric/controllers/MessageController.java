package unicauca.edu.co.sga.evaluation_service.adapters.rubric.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.evaluation_service.adapters.messaging.RabbitMQProducer;
import unicauca.edu.co.sga.evaluation_service.domain.models.EnrollRabbit;


// TODO: This CONTROLLER needs to be deleting. ALREADY IS USED IN SERVICE FOLDER.

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
public class MessageController {
    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping("/sendEnroll")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN_ROLE')")
    public String sendMessage(@RequestBody EnrollRabbit message){
//        rabbitMQProducer.sendEnroll(message.getMessage()); TODO Do the exchange and key with message.
        return "Message sent: " + message.getMessage();
    }
}

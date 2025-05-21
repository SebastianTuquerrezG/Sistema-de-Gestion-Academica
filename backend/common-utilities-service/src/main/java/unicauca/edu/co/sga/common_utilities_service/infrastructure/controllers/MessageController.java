package unicauca.edu.co.sga.common_utilities_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import unicauca.edu.co.sga.common_utilities_service.adapters.messaging.RabbitMQProducer;


// TODO: This CONTROLLER needs to be deleting. ALREADY IS USED IN SERVICE FOLDER.

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
public class MessageController {
    private final RabbitMQProducer rabbitMQProducer;

//    @PostMapping("/sendStudent")
//    public String sendMessage(@RequestBody StudentRabbit message){
//        rabbitMQProducer.sendMessage(message.getMessage());
//        return "Message sent: " + message.getMessage();
//    }
}

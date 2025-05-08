package unicauca.edu.co.sga.helper_service.adapters.messaging;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Data
public class RabbitMQReceive {
    private final CountDownLatch latch = new CountDownLatch(1);
}

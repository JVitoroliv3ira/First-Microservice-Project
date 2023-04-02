package api.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GreetingsEmailListener {

    @KafkaListener(topics = "greetings-email", groupId = "greetings-email-group")
    public void listen(String message) {
        log.info("Thread: {}", Thread.currentThread());
        log.info("Mensagem: {}", message);
    }
}

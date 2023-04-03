package api.listeners;

import api.services.GreetingsEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GreetingsEmailListener {

    private final GreetingsEmailService emailService;
    private final String host;
    private final Integer port;
    private final String email;
    private final String username;
    private final String password;
    private final String protocol;

    public GreetingsEmailListener(
            GreetingsEmailService emailService,
            @Value("${email-property.host}") String host,
            @Value("${email-property.port}") Integer port,
            @Value("${email-property.email}") String email,
            @Value("${email-property.username}") String username,
            @Value("${email-property.password}") String password,
            @Value("${email-property.protocol}") String protocol
    ) {
        this.emailService = emailService;
        this.host = host;
        this.port = port;
        this.email = email;
        this.username = username;
        this.password = password;
        this.protocol = protocol;
    }

    @KafkaListener(topics = "greetings-email", groupId = "greetings-email-group")
    public void listen(String to) {
        this.emailService.setupMailSender(this.host, this.port, this.username, this.password, this.protocol);
        this.emailService.send(
                this.email,
                to,
                "Bem-vindo(a) à nossa aplicação!",
                String.format("Olá %s, seja bem-vindo(a) à nossa aplicação. Estamos felizes em tê-lo(a) aqui!", to)
        );
    }
}

package api.services;

import api.contracts.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class GreetingsEmailService implements IEmailService {
    private final JavaMailSender mailSender;

    public GreetingsEmailService() {
        this.mailSender = new JavaMailSenderImpl();
    }

    @Override
    public void setupMailSender(String host, Integer port, String username, String password, String protocol) {
        ((JavaMailSenderImpl) this.mailSender).setHost(host);
        ((JavaMailSenderImpl) this.mailSender).setPort(port);
        ((JavaMailSenderImpl) this.mailSender).setUsername(username);
        ((JavaMailSenderImpl) this.mailSender).setPassword(password);
        ((JavaMailSenderImpl) this.mailSender).setProtocol(protocol);
    }

    @Override
    public SimpleMailMessage buildMessage(String from, String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        return message;
    }

    @Override
    public void send(String from, String to, String subject, String content) {
        SimpleMailMessage message = this.buildMessage(from, to, subject, content);
        this.mailSender.send(message);
    }
}

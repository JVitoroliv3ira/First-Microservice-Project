package api.contracts;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailService {
    void setupMailSender(String host, Integer port, String email, String password, String protocol);

    SimpleMailMessage buildMessage(String from, String to, String subject, String content);

    void send(String from, String to, String subject, String content);

}

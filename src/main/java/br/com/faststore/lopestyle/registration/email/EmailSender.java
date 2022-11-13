package br.com.faststore.lopestyle.registration.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.faststore.lopestyle.models.Order;
import br.com.faststore.lopestyle.models.User;

public interface EmailSender {
    
    void send(String to, String email);

    void sendEmail(SimpleMailMessage message);

    void sendOrderConfirmationHtmlEmail(Order order);
    
    void sendHtmlEmail(MimeMessage message);

    void sendOrderConfirmationEmail(Order order);

    void sendNewPasswordEmail(User user, String newPass);
}

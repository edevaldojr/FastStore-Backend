package br.com.faststore.lopestyle.registration.email;

import javax.mail.MessagingException;

import br.com.faststore.lopestyle.models.Order;
import br.com.faststore.lopestyle.models.User;

public interface EmailSender {
    
    void send(String to, String email, String subject);

    void sendOrderConfirmationHtmlEmail(Order order);
    
    void sendOrderConfirmationEmail(Order order);

    void sendNewPasswordEmail(User user, String newPass) throws MessagingException ;
}

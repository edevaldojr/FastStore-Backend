package br.com.faststore.lopestyle.registration.email;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.faststore.lopestyle.models.Order;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EmailService implements EmailSender{

    @Value("${default.sender}")
    private String sender;
    
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Order obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getConsumer().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirme seu email");
            helper.setFrom(sender);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            log.error("failed to send email", exception);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Order obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mm);
        } catch (Exception e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromPedido(Order obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getConsumer().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(obj), true);

        return mimeMessage;
    }

    protected String htmlFromTemplatePedido(Order obj) {
        Context context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Simulando envio de email HTML...");
        log.info(msg.toString());
        log.info("Email enviado");
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Simulando envio de email...");
        log.info(msg.toString());
        log.info("Email enviado");
    }


}
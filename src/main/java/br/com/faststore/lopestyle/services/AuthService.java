package br.com.faststore.lopestyle.services;

import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.models.User;
import br.com.faststore.lopestyle.registration.email.EmailService;
import br.com.faststore.lopestyle.repositories.UserRepository;
import br.com.faststore.lopestyle.security.UserSS;
import br.com.faststore.lopestyle.services.Exceptions.AuthorizationException;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Email não encontrado"));

        String newPass = newPassword();
        user.setPassword(pe.encode(newPass));

        userRepository.save(user);
        emailService.sendNewPasswordEmail(user, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) { // gera um digito
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) { // gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        } else { // gera letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }


    public void changePassword(String email, String newPassword) {
        UserSS userSS = UserService.authenticated();
        if (userSS == null || !email.equals(userSS.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Email não encontrado"));

        user.setPassword(pe.encode(newPassword));

        userRepository.save(user);
    }

}
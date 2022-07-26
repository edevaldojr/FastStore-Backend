package br.com.faststore.lopestyle.registration.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.models.Consumer;
import br.com.faststore.lopestyle.models.Employee;
import br.com.faststore.lopestyle.models.User;
import br.com.faststore.lopestyle.models.enums.Perfil;
import br.com.faststore.lopestyle.registration.email.EmailSender;
import br.com.faststore.lopestyle.registration.token.ConfirmationToken;
import br.com.faststore.lopestyle.registration.token.ConfirmationTokenService;
import br.com.faststore.lopestyle.repositories.ConsumerRepository;
import br.com.faststore.lopestyle.repositories.EmployeeRepository;
import br.com.faststore.lopestyle.repositories.UserRepository;
import br.com.faststore.lopestyle.services.UserDetailsServiceImpl;
import br.com.faststore.lopestyle.services.Exceptions.ObjectAlreadyExistsException;

@Service
public class RegistrationService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private EmailSender emailSender;

    @Value("${default.server}")
    private String server;

    
    public String register(Consumer consumer) {
        consumer.addPerfil(Perfil.CONSUMER);
        consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));

        boolean isValidEmail = emailValidator.test(consumer.getEmail());

        if(!isValidEmail) {
            throw new IllegalStateException("Email não validado");
        }

        Optional<User> userExists = userRepository.findByEmail(consumer.getEmail());

        if(userExists.isPresent()){
            throw new ObjectAlreadyExistsException("Email já cadastrado.");
        }

        Optional<Consumer> cpfExists = consumerRepository.findByCpf(consumer.getCpf());
        if(cpfExists.isPresent()){
            throw new ObjectAlreadyExistsException("Cpf já cadastrado.");
        }

        consumerRepository.save(consumer);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), consumer);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = this.server + "/api/v1/registration/confirm?token=" + token;
        
        emailSender.send(
            consumer.getEmail(),
            buildEmail(consumer.getFirstName(), link), "Confirme seu email");

        return token;
    }

    public String registerEmployee(Employee employee) {
        employee.addPerfil(Perfil.EMPLOYEE);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        if(employee.isAdmin()) employee.addPerfil(Perfil.ADMIN);

        boolean isValidEmail = emailValidator.test(employee.getEmail());

        if(!isValidEmail) {
            throw new IllegalStateException("Email não validado");
        }

        Optional<User> userExists = userRepository.findByEmail(employee.getEmail());

        if(userExists.isPresent()){
            throw new ObjectAlreadyExistsException("Email já cadastrado.");
        }

        employeeRepository.save(employee);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), employee);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = this.server + "/api/v1/registration/confirm?token=" + token;
        
        emailSender.send(
            employee.getEmail(),
            buildEmail(employee.getFirstName(), link), "Confirme seu email");

        return token;
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token não encontrado!"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email já confirmado!");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiratesAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expirado");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        return "Confirmado";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirme seu email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Olá " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Obrigado por registrar. Por favor clique no link abaixo para ativar sua conta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Ative agora</a> </p></blockquote>\n Link irá expirar em 15 minutos. <p>Até mais!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
    
}

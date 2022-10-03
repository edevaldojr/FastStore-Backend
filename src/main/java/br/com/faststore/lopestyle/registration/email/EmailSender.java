package br.com.faststore.lopestyle.registration.email;

public interface EmailSender {
    
    void send(String to, String email);
}

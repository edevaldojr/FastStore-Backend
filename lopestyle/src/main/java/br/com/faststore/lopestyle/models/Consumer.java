package br.com.faststore.lopestyle.models;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class Consumer {
    
    private int id;
    private User user;
    private String cpf;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthDate;
    private String sexo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}

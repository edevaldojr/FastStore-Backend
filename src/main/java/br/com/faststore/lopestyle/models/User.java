package br.com.faststore.lopestyle.models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
    
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}

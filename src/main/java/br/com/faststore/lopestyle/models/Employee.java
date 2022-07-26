package br.com.faststore.lopestyle.models;

import lombok.Data;

@Data
public class Employee {
    
    private int id;
    private User user;
    private String firstName;
    private String lastName;
    private boolean admin;
    private boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

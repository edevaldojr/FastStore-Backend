package br.com.faststore.lopestyle.models;

import lombok.Data;

@Data
public class User {
    
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}

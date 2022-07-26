package br.com.faststore.lopestyle.models;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
public class Consumer extends User {
    
    private int id;
    private String cpf;
    private String phoneNumber;
    private Date birthDate;
    private String sexo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}

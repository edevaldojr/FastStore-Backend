package br.com.faststore.lopestyle.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
public class Employee extends User {
    
    private int id;
    private boolean admin;
    private boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package br.com.faststore.lopestyle.models;


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

}

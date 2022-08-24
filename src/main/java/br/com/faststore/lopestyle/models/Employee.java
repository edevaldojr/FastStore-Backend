package br.com.faststore.lopestyle.models;



import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@Entity
public class Employee extends User {
    
    private boolean admin;
    private boolean active;

}

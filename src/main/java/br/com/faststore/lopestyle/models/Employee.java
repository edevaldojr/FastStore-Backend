package br.com.faststore.lopestyle.models;



import java.util.Calendar;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Employee extends User {
    
    private boolean admin;
    private boolean active;

    @Builder
    public Employee(int id, String email, String password, String firstName, String lastName, Calendar createdAt,
    Calendar updatedAt, boolean admin, boolean active) {
        super(id, email, password, firstName, lastName, createdAt, updatedAt);
        this.admin = admin;
        this.active = active;
    }

}

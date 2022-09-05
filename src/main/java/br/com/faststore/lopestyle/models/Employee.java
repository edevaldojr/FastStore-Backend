package br.com.faststore.lopestyle.models;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.faststore.lopestyle.models.enums.Perfil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Employee extends User {
    @Id
    private int id;
    private boolean admin;
    private boolean active;

    @Builder
    public Employee(int id, String email, String password, String firstName, String lastName, Set<Integer> perfis, Calendar createdAt,
    Calendar updatedAt, boolean admin, boolean active) {
        super(id, email, password, firstName, lastName, perfis,createdAt, updatedAt);
        this.id = id;
        this.admin = admin;
        this.active = active;
        this.addPerfil(Perfil.EMPLOYEE);
    }

}

package br.com.faststore.lopestyle.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.faststore.lopestyle.models.utils.SexType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Consumer extends User {
    
    private String cpf;
    private String phoneNumber;
	@JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private SexType sexo;
    private boolean active;
    @OneToMany
    private List<Order> orders;

    @Builder
    public Consumer(int id, String email, String password, String firstName, String lastName, Calendar createdAt,
            Calendar updatedAt, String cpf, String phoneNumber, Date birthDate, SexType sexo, boolean active,
            List<Order> orders) {
        super(id, email, password, firstName, lastName, createdAt, updatedAt);
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.sexo = sexo;
        this.active = active;
        this.orders = orders;
    }

 
}

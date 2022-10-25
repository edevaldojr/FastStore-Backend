package br.com.faststore.lopestyle.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.faststore.lopestyle.models.enums.SexType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Entity
public class Consumer extends User {
    
    @Id
    private int id;
    @Column(unique = true)
    private String cpf;
    private String phoneNumber;
	@JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private SexType sexo;

    @OneToMany
    private List<Order> orders;

    @Builder
    public Consumer(int id, String email, String password, String firstName, String lastName, Set<Integer> perfis, Calendar createdAt,
            Calendar updatedAt, String cpf, String phoneNumber, Date birthDate, SexType sexo, boolean active,
            List<Order> orders) {
        super(id, email, password, firstName, lastName, perfis, createdAt, updatedAt, active);
        this.id = id;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.sexo = sexo;
        this.orders = orders;
    }

 
}

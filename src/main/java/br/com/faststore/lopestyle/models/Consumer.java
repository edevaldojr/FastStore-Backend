package br.com.faststore.lopestyle.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.faststore.lopestyle.models.utils.SexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
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
    
}

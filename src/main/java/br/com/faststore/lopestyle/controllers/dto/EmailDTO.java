package br.com.faststore.lopestyle.controllers.dto;

import java.io.Serializable;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailDTO implements Serializable {

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

}

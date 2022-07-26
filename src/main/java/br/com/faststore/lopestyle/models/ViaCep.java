package br.com.faststore.lopestyle.models;

import lombok.Data;

@Data
public class ViaCep {
    
    private int id;
    private String cep;
    private String logadouro;
    private String bairro;
    private String localidade;
    private String uf;
    
}

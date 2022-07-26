package br.com.faststore.lopestyle.models;

import lombok.Data;

@Data
public class Address {
    
    private int id;
    private String number;
    private String complemento;
    private ViaCep addressFull;
}

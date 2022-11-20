package br.com.faststore.lopestyle.correios.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "Servicos")
public class ShippingResponse implements Serializable {
    
    public ShippingResponseObject cServico;
}

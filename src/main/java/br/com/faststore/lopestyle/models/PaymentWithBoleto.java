package br.com.faststore.lopestyle.models;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@Entity
@JsonTypeName("paymentWithBoleto")
@Data
public class PaymentWithBoleto extends Payment{
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date expirationDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date paymentDate;

}

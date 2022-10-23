package br.com.faststore.lopestyle.models;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@JsonTypeName("paymentWithBoleto")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentWithBoleto extends Payment{
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date expirationDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date paymentDate;

}

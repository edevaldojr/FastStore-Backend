package br.com.faststore.lopestyle.models;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.faststore.lopestyle.models.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@JsonTypeName("paymentWithCreditCard")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentWithCreditCard extends Payment {

    private Integer numeroDeParcelas;


    public PaymentWithCreditCard(int id, PaymentStatus status, Order order, Integer numeroDeParcelas) {
        super(id, status, order);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
}

package br.com.faststore.lopestyle.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.faststore.lopestyle.models.utils.PaymentMethod;
import br.com.faststore.lopestyle.models.utils.PaymentStatus;
import lombok.Data;

@Data
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private PaymentStatus status;
    private PaymentMethod method;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "orderId")
    @MapsId
    private Order order;
    
}
package br.com.faststore.lopestyle.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode
public class OrderProduct {

    @JsonIgnore
    @EmbeddedId
    private OrderProductPK id = new OrderProductPK();
    private int quantity;
    private double discount;
    private double unityValue;
    private double totalValue;


    public OrderProduct(Order order, Product product, Double discount, int quantity, Double unityValue, Double totalValue) {
        id.setOrder(order);
        id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.unityValue = unityValue;
        this.unityValue = totalValue;
    }

}

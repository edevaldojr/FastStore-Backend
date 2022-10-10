package br.com.faststore.lopestyle.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

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


    public OrderProduct(Order order, Stock stock, Product product, Double discount, int quantity, Double unityValue) {
        id.setOrder(order);
        id.setProduct(product);
        id.setStock(stock);
        this.discount = discount;
        this.quantity = quantity;
        this.unityValue = unityValue;
    }


    public double getSubTotal() {
        return (unityValue - discount) * quantity;
    }

    public Stock getStock() {
        return id.getStock();
    }

    public void setStock(Stock stock) {
        id.setStock(stock);
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    public OrderProductPK getId() {
        return id;
    }

    public void setId(OrderProductPK id) {
        this.id = id;
    }


}

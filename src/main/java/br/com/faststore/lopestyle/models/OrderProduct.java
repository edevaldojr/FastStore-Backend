package br.com.faststore.lopestyle.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    @JsonIgnore
    @EmbeddedId
    private OrderProductStockPK id = new OrderProductStockPK();
    
    private int quantity;
    private double discount;
    private double unityValue;


    public OrderProduct(Order order, Product product, Stock stock, Double discount, int quantity, Double unityValue) {
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

    public OrderProductStockPK getId() {
        return id;
    }

    public void setId(OrderProductStockPK id) {
        this.id = id;
    }


}

package br.com.faststore.lopestyle.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Embeddable
@EqualsAndHashCode(exclude="order")
@ToString(exclude="order")
public class OrderProductStockPK implements Serializable {
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;   
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

}

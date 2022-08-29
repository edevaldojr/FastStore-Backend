package br.com.faststore.lopestyle.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class OrderProduct {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private int discount;
    private double unityValue;
    private double totalValue;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    private Order order;

}

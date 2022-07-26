package br.com.faststore.lopestyle.models;

import lombok.Data;

@Data
public class OrderProduct {

    private int id;
    private int quantity;
    private int discount;
    private double unityValue;
    private double totalValue;
    private Product product;

}

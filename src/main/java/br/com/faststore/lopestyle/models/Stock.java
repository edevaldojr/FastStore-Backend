package br.com.faststore.lopestyle.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Stock {
    
    @Id
    private int id;
    private int quantity;
    private String color;
    private String size;

    @ManyToOne
    private Product product;

}

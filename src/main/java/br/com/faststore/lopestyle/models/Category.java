package br.com.faststore.lopestyle.models;

import java.util.List;

import lombok.Data;

@Data
public class Category {
    
    private int id;
    private String name;
    private List<Product> products;
    
}

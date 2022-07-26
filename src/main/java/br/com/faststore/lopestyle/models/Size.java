package br.com.faststore.lopestyle.models;

import java.util.List;

import lombok.Data;

@Data
public class Size {
    
    private int id;
    private String name;
    private List<Product> products;
    
}

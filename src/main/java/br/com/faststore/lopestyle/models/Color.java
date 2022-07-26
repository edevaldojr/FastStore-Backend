package br.com.faststore.lopestyle.models;

import java.util.List;

import lombok.Data;

@Data
public class Color {
    
    private int id;
    private String name;
    private List<Product> products;

}

package br.com.faststore.lopestyle.models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Product {
    
    private int id;
    private String sku;
    private String name;
    private String brand;
    private String description;
    private int inventory;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

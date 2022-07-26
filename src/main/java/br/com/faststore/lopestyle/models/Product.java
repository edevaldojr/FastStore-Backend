package br.com.faststore.lopestyle.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Product {
    
    private int id;
    private String sku;
    private String name;
    private String brand;
    private String description;
    private int inventory;
    private List<Category> categories;
    private List<Size> sizes;
    private List<Color> colors;
    private List<Image> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

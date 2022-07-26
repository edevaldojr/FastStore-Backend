package br.com.faststore.lopestyle.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDate  createdAt;
    private LocalDate updatedAt;

}

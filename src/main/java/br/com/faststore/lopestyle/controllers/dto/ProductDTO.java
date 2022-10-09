package br.com.faststore.lopestyle.controllers.dto;

import java.io.Serializable;
import java.util.List;

import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.models.Stock;
import lombok.Data;

@Data
public class ProductDTO implements Serializable{
    
    private Product product;
    private List<Stock> stock;
    private int categoryId;
    private int imageId; 
}

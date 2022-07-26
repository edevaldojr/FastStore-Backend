package br.com.faststore.lopestyle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("id") String productId) {
        return null;
    }


}

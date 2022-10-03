package br.com.faststore.lopestyle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.services.ProductService;

@RestController
@RequestMapping("dashboard/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") int productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getProducts(@RequestBody FilterDto productsFilterDto) {
        Page<Product> products = productService.getProductsPageable(productsFilterDto);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestBody FilterDto productsFilterDto) {
        List<Product> products = productService.getBySearch(productsFilterDto);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/insertProduct")
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.insertProduct(product));
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int productId,@RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(productId, product));
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }




}

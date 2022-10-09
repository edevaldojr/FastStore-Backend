package br.com.faststore.lopestyle.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.controllers.dto.ProductDTO;
import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.models.Stock;
import br.com.faststore.lopestyle.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") int productId) {
        return ResponseEntity.ok().body(productService.getProduct(productId));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getProducts(@RequestBody FilterDto productsFilterDto) {
        Page<Product> products = productService.getProductsPageable(productsFilterDto);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestBody FilterDto productsFilterDto) {
        List<Product> products = productService.getBySearch(productsFilterDto);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/product/{categoryId}")
    public ResponseEntity<Product> insertProduct(@PathVariable("categoryId") int categoryId, @RequestBody ProductDTO  productDTO) {
        Product product = productService.insertProduct(categoryId, productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductDTO productDTO) {
        productService.updateProduct(productId, productDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/stock/{productId}")
    public ResponseEntity<Page<Stock>> getStocksFromProduct(@PathVariable("productId") int productId, @RequestBody FilterDto filterDto) {
        return ResponseEntity.ok().body(productService.getStockFromProduct(productId, filterDto));
    }

}

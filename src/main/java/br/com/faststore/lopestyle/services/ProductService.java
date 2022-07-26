package br.com.faststore.lopestyle.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.repositories.ProductRepository;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product getProduct(int productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        return product;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products = repository.findAll();
        return products;
    }

    public Product createProduct(Product product) {
        LocalDate dateNow = LocalDate.now();
        product.setCreatedAt(dateNow);
        product.setUpdatedAt(dateNow);
        return repository.save(product);
    }

    public Product updateProduct(int productId, Product product) {
        Product prod = repository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        LocalDate dateNow = LocalDate.now();
        prod = Product.builder()
                        .sku(product.getSku())
                        .name(product.getName())
                        .brand(product.getBrand())
                        .inventory(product.getInventory())
                        .categories(product.getCategories())
                        .colors(product.getColors())
                        .sizes(product.getSizes())
                        .images(product.getImages())
                        .description(product.getDescription())
                        .createdAt(product.getCreatedAt())
                        .updatedAt(dateNow)
                        .build();
                        
        return repository.save(prod);
    }

    public void deleteProduct(int productId) {
        Product prod = repository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        repository.delete(prod);
    }
    
    
}

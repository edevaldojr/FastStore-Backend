package br.com.faststore.lopestyle.dashboard.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.dashboard.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.dashboard.services.Exceptions.ObjectNotFoundException;
import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product getProduct(int productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        return product;
    }

    public Page<Product> getProductsPageable(FilterDto productsFilterDto) {
        PageRequest pageable = PageRequest.of(productsFilterDto.getPage(), productsFilterDto.getPageSize());
        Page<Product> products = repository.findAll(pageable);
        return products;
    }

    public List<Product> getBySearch(FilterDto productsFilterDto) {
        List<Product> products = repository.findByNameStartingWith(productsFilterDto.getSearch());
        return products;
    }

    public Product insertProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(int productId, Product product) {
        Product prod = repository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        Calendar dateNow = Calendar.getInstance();
        prod = Product.builder()
                        .id(productId)
                        .sku(product.getSku())
                        .name(product.getName())
                        .brand(product.getBrand())
                        .inventory(product.getInventory())
                        .categories(product.getCategories())
                        .colors(product.getColors())
                        .sizes(product.getSizes())
                        .images(product.getImages())
                        .description(product.getDescription())
                        .createdAt(prod.getCreatedAt())
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

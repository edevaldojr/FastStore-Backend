package br.com.faststore.lopestyle.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Category;
import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.repositories.CategoryRepository;
import br.com.faststore.lopestyle.repositories.ProductRepository;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        List<Product> products = repository.findByNameContaining(productsFilterDto.getSearch());
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
                        .category(product.getCategory())
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

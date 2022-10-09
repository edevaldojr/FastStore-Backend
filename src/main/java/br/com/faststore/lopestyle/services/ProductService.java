package br.com.faststore.lopestyle.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.controllers.dto.ProductDTO;
import br.com.faststore.lopestyle.models.Category;
import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.models.Stock;
import br.com.faststore.lopestyle.repositories.CategoryRepository;
import br.com.faststore.lopestyle.repositories.ProductRepository;
import br.com.faststore.lopestyle.repositories.StockRepository;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StockRepository stockRepository;

    public Product getProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
                                "Produto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        return product;
    }

    public Page<Stock> getStockFromProduct(int productId, FilterDto filterDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
                                "Produto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        PageRequest pageable = PageRequest.of(filterDto.getPage(), filterDto.getPageSize());
        Page<Stock> productStock = new PageImpl<>(product.getStock(), pageable, product.getStock().size());
        
        return productStock;
    }

    public Page<Product> getProductsPageable(FilterDto productsFilterDto) {
        PageRequest pageable = PageRequest.of(productsFilterDto.getPage(), productsFilterDto.getPageSize());
        Page<Product> products = productRepository.findAllByActiveTrue(pageable);
        return products;
    }

    public List<Product> getBySearch(FilterDto productsFilterDto) {
        List<Product> products = productRepository.findByActiveTrueAndNameContaining(productsFilterDto.getSearch());
        return products;
    }

    public Product insertProduct(int categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException(
            "Categoria não encontrada! Id: " + categoryId + ", Tipo: " + Category.class.getName()));
        Product product = productDTO.getProduct();

        product.setCategory(category);
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(int productId, ProductDTO productDTO) {
        Product prod = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Produto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new ObjectNotFoundException(
            "Categoria não encontrada! Id: " + productDTO.getCategoryId() + ", Tipo: " + Category.class.getName()));

        Calendar dateNow = Calendar.getInstance();
        prod = Product.builder()
                        .id(productId)
                        .sku(prod.getSku())
                        .name(productDTO.getProduct().getName())
                        .brand(productDTO.getProduct().getBrand())
                        .category(category)
                        .description(productDTO.getProduct().getDescription())
                        .createdAt(prod.getCreatedAt())
                        .updatedAt(dateNow)
                        .build();
                        
        return productRepository.save(prod);
    }

    public void deleteProduct(int productId) {
        Product prod = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Produto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        prod.setActive(false);
        productRepository.save(prod);
    }

    public void setProductToStock(ProductDTO productDTO){
        Product product = productRepository.findBySku(productDTO.getProduct().getSku()); 
        List<Stock> stocks = productDTO.getStock();

        product.setStock(stocks);
        stockRepository.saveAll(stocks);
        productRepository.save(product);
    }

}

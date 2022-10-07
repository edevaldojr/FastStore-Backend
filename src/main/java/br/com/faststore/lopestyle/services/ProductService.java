package br.com.faststore.lopestyle.services;

import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.controllers.dto.ProductStockDTO;
import br.com.faststore.lopestyle.models.Category;
import br.com.faststore.lopestyle.models.Image;
import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.models.Stock;
import br.com.faststore.lopestyle.repositories.CategoryRepository;
import br.com.faststore.lopestyle.repositories.ImageRepository;
import br.com.faststore.lopestyle.repositories.ProductRepository;
import br.com.faststore.lopestyle.repositories.StockRepository;
import br.com.faststore.lopestyle.security.UserSS;
import br.com.faststore.lopestyle.services.Exceptions.AuthorizationException;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private S3Service s3Service;


    public Product getProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        return product;
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

    public Product insertProduct(int categoryId, ProductStockDTO productStockDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException(
            "Categoria não encontrada! Id: " + categoryId + ", Tipo: " + Category.class.getName()));
        Product product = productStockDTO.getProduct();

        product.setCategory(category);
        productRepository.save(product);
        setProductToStock(productStockDTO);
        return product;
    }

    public Product updateProduct(int productId, ProductStockDTO productDTO) {
        Product prod = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new ObjectNotFoundException(
            "Categoria não encontrada! Id: " + productDTO.getCategoryId() + ", Tipo: " + Category.class.getName()));

        Calendar dateNow = Calendar.getInstance();
        prod = Product.builder()
                        .id(productId)
                        .sku(productDTO.getProduct().getSku())
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
            "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));
        prod.setActive(false);
        productRepository.save(prod);
    }
    
    public void setProductToStock(ProductStockDTO productStockDTO){
        
        List<Stock> stocks = productStockDTO.getStock();
        stocks.stream().forEach(s -> {
            s.setProduct(productStockDTO.getProduct());
        });
        productStockDTO.getProduct().setStocks(stocks);
        stockRepository.saveAll(stocks);
        Product product = productRepository.findBySku(productStockDTO.getProduct().getSku());
        product.setStocks(stocks);
        productRepository.save(product);
    }


    public URI uploadProductPicture(MultipartFile multipartFile, int productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));

        Image imageProduct = new Image();
        imageProduct.setProduct(product);
        
        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

        String fileName = product.getName() + "-" + product.getSku() +"-" + product.getImages().size() + ".jpg";
        URI imageUri = s3Service.uploadFile(imageService.getInputStream(jpgImage,"jpg"), fileName, "image");

        imageProduct.setUrlImage(imageUri.toString());
        imageRepository.save(imageProduct);
        return imageUri;
    }

}

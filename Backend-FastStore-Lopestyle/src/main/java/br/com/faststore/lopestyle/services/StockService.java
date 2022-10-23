package br.com.faststore.lopestyle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.models.Stock;
import br.com.faststore.lopestyle.repositories.ProductRepository;
import br.com.faststore.lopestyle.repositories.StockRepository;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

@Service
public class StockService {
    
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    public Stock insertStock(int productId, Stock stock){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Produto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));

        List<Stock> productStock = product.getStock();
        productStock.add(stock);
        product.setStock(productStock);
        stock = stockRepository.save(stock);
        productRepository.save(product);
        return stock;
    }

    public void removeStock(int productId, int stockId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Produto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));

        Stock productStock = stockRepository.findById(stockId).orElseThrow(() -> new ObjectNotFoundException(
            "Estoque não encontrado! Id: " + stockId + ", Tipo: " + Stock.class.getName()));

        product.removeStockFromProduct(productStock);
        productRepository.save(product);
        stockRepository.deleteById(stockId);
    }



}

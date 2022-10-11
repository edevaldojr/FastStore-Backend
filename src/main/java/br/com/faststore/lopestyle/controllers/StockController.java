package br.com.faststore.lopestyle.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.faststore.lopestyle.models.Stock;
import br.com.faststore.lopestyle.services.StockService;

@RequestMapping("/stock")
@RestController
public class StockController {
    
    @Autowired
    private StockService stockService;
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{productId}")
    public ResponseEntity<Void> insertStock(@PathVariable("productId") int productId, @RequestBody Stock stock){
        stock = stockService.insertStock(productId, stock);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(stock.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{productId}/{stockId}")
    public ResponseEntity<Void> deleteStock(@PathVariable("productId") int productId, @PathVariable("stockId") int stockId){
        stockService.removeStock(productId, stockId);
        return ResponseEntity.noContent().build();
    }

}

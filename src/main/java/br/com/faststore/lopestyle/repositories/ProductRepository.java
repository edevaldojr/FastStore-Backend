package br.com.faststore.lopestyle.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
    List<Product> findByNameContaining(String infix);
    
}

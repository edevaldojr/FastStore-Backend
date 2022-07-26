package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Product;

@Repository
public interface ConsumerRepository extends JpaRepository<Product, Integer>{
    
}

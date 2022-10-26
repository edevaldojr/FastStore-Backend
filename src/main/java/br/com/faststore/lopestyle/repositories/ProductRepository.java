package br.com.faststore.lopestyle.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
    List<Product> findByActiveTrueAndNameContaining(String infix);

    Page<Product> findAllByActiveTrue(Pageable pageable);

    Page<Product> findAllByCategoryIdAndActiveTrue(int categoryId, Pageable pageable);

    Product findBySku(String sku);
    
}

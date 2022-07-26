package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faststore.lopestyle.models.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer>{
    
}

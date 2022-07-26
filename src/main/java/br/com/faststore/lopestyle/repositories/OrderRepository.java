package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    
}

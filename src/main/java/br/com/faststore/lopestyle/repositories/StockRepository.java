package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>{
    


}

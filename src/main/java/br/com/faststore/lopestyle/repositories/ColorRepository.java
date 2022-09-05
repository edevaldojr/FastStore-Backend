package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer>{
    
}

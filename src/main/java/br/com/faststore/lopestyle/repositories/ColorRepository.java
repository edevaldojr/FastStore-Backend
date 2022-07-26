package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faststore.lopestyle.models.Color;

public interface ColorRepository extends JpaRepository<Color, Integer>{
    
}

package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faststore.lopestyle.models.Image;

public interface ImageRepository extends JpaRepository<Image, Integer>{
    
}

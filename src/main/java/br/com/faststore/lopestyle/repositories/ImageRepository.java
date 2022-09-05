package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{
    
}

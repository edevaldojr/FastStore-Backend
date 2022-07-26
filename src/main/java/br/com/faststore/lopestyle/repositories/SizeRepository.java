package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faststore.lopestyle.models.Size;

public interface SizeRepository extends JpaRepository<Size, Integer>{
    
}

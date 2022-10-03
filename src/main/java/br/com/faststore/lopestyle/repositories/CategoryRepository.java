package br.com.faststore.lopestyle.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
 
    Optional<Category> findByName(String name);

}
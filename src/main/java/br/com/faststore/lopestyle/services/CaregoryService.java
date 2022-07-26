package br.com.faststore.lopestyle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.models.Category;
import br.com.faststore.lopestyle.repositories.CategoryRepository;
import br.com.faststore.lopestyle.services.Exceptions.ObjectAlreadyExistsException;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

@Service
public class CaregoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> getAllCategories() {
        List<Category> categorys = repository.findAll();
        return categorys;
    }

    public Category insertCategory(Category category) {
        Optional<Category> cat = repository.findByName(category.getName());
        if(cat.isPresent()){
            throw new ObjectAlreadyExistsException(
            "Objeto já existente! Id: " + cat.get().getId() + ", Tipo: " + Category.class.getName());
        }
        return repository.save(category);
    }

    public Category updateCategory(int categoryId, Category updatedCategory) {
        Category category = repository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + categoryId + ", Tipo: " + Category.class.getName()));
        category = Category.builder()
                            .id(categoryId)
                            .name(updatedCategory.getName())
                            .build();
        return repository.save(category);
    }

    public void deleteCategory(int categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + categoryId + ", Tipo: " + Category.class.getName()));
        category.setActive(false);
        repository.save(category);
    }
    

}

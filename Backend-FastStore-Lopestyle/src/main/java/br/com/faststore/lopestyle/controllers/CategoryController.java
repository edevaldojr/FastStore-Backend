package br.com.faststore.lopestyle.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Category;
import br.com.faststore.lopestyle.services.CaregoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CaregoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<Page<Category>> getCategories(@RequestBody FilterDto categorysFilterDto) {
        Page<Category> categories = categoryService.getAllCategories(categorysFilterDto);
        return ResponseEntity.ok(categories);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/category")
    public ResponseEntity<Category> insertCategory(@RequestBody Category category) {
        category = categoryService.insertCategory(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable("categoryId") int categoryId,@RequestBody Category category) {
        return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }


}

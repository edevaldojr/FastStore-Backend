package br.com.faststore.lopestyle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Category;
import br.com.faststore.lopestyle.services.CaregoryService;

@RestController
@RequestMapping("dashboard/category")
public class CategoryController {
    
    @Autowired
    private CaregoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<Page<Category>> getCategories(@RequestBody FilterDto categorysFilterDto) {
        Page<Category> categories = categoryService.getAllCategories(categorysFilterDto);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/insertCategory")
    public ResponseEntity<Category> insertCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.insertCategory(category));
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") int categoryId,@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, category));
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }


}

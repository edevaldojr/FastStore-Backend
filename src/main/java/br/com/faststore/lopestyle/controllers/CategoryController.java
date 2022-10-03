package br.com.faststore.lopestyle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Category>> getCategorys(@RequestBody FilterDto categorysFilterDto) {
        List<Category> Categorys = categoryService.getAllCategories();
        return ResponseEntity.ok(Categorys);
    }

    @PostMapping("/insertCategory")
    public ResponseEntity<Category> insertCategory(@RequestBody Category Category) {
        return ResponseEntity.ok(categoryService.insertCategory(Category));
    }

    @PutMapping("/updateCategory/{CategoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("CategoryId") int CategoryId,@RequestBody Category Category) {
        return ResponseEntity.ok(categoryService.updateCategory(CategoryId, Category));
    }

    @DeleteMapping("/deleteCategory/{CategoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("CategoryId") int CategoryId) {
        categoryService.deleteCategory(CategoryId);
        return ResponseEntity.noContent().build();
    }


}

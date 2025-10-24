package com.ecommerce.project.sb_ecom.com.ecommerce.project.controller;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/api/public/category")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> allCategories = service.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping("/api/public/category")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
         service.createNewCategory(category);
        return new ResponseEntity<>("Category added successfully " + category.getCategoryName(), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId){
        try{
            String status = service.deleteCategoryById(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }

    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId){
        try{
            Category savedCategory = service.updateCategoryById(category, categoryId);
            return new ResponseEntity<>("category updated with id " + categoryId, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}

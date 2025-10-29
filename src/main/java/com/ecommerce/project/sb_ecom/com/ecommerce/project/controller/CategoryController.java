package com.ecommerce.project.sb_ecom.com.ecommerce.project.controller;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/api/public/category")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse categoryResponse = service.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/api/public/category")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
         service.createNewCategory(category);
        return new ResponseEntity<>("Category added successfully ", HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId){
            String status = service.deleteCategoryById(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);

    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId){
            Category savedCategory = service.updateCategoryById(category, categoryId);
            return new ResponseEntity<>("category updated with id " + categoryId, HttpStatus.OK);
    }
}

package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository repository;

    private List<Category> categories = new ArrayList<>();
    //private long catId = 1l;

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public String createNewCategory(Category category) {
        //category.setCategoryId(catId++);
        repository.save(category);
        return "Category created successfully";
    }

    @Override
    public String deleteCategoryById(Long categoryId) {

        Category category= repository.findById(categoryId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(category);

        return "Category removed successfully " + category.getCategoryName();
        }

    @Override
    public Category updateCategoryById(Category category, Long categoryId) {

        long savedCategoryId = repository.findById(categoryId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getCategoryId();

        category.setCategoryId(categoryId);
        return repository.save(category);


    }
}


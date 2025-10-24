package com.ecommerce.project.sb_ecom.com.ecommerce.project.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CategoryServiceImp implements CategoryService{

    private List<Category> categories = new ArrayList<>();
    private long catId = 1l;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String createNewCategory(Category category) {
        category.setCategoryId(catId++);
        categories.add(category);
        return "Category created successfully";
    }

    @Override
    public String deleteCategoryById(Long categoryId) {
        Predicate<? super Category> predicate = cat -> cat.getCategoryId() == categoryId ;
        Category category = categories.stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            categories.remove(category);
            return "Category removed successfully " + category.getCategoryName();
        }

    @Override
    public Category updateCategoryById(Category category, Long categoryId) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(cat -> cat.getCategoryId() == categoryId)
                .findFirst();

        if(optionalCategory.isPresent()){
            Category existing_category = optionalCategory.get();
            existing_category.setCategoryName(category.getCategoryName());
            return existing_category;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }

    }
}


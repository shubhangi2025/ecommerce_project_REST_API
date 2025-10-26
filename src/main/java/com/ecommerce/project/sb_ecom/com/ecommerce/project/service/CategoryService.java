package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;

import java.util.List;


public interface CategoryService {

    public List<Category> getAllCategories();

    public String createNewCategory(Category category);

    String deleteCategoryById(Long categoryId);

    Category updateCategoryById(Category category, Long categoryId);
}

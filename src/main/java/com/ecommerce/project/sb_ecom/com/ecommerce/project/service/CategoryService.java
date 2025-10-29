package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.CategoryResponse;

import java.util.List;


public interface CategoryService {

    public CategoryResponse getAllCategories();

    public String createNewCategory(Category category);

    String deleteCategoryById(Long categoryId);

    Category updateCategoryById(Category category, Long categoryId);
}

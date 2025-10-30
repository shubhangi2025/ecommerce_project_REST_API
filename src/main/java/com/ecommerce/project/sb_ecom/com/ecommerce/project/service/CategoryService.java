package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.CategoryResponse;


public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createNewCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategoryById(Long categoryId);

    CategoryDTO updateCategoryById(CategoryDTO categoryDTO, Long categoryId);
}

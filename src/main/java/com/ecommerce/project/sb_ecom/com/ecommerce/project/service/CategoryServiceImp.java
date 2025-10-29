package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.exception.APIException;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private List<Category> categories = new ArrayList<>();

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categoryList = repository.findAll();
        if(categoryList.isEmpty())
            throw new APIException("No categories available");

        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);

        return categoryResponse;
    }

    @Override
    public String createNewCategory(Category category) {
        Category savedCategory = repository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null){
            throw new APIException(category.getCategoryName()+ " already available");
        }else
            repository.save(category);
            return "Category created successfully";
    }

    @Override
    public String deleteCategoryById(Long categoryId) {

        Category category= repository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        repository.delete(category);

        return "Category removed successfully " + category.getCategoryName();
        }

    @Override
    public Category updateCategoryById(Category category, Long categoryId) {

        long savedCategoryId = repository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId)).getCategoryId();

        category.setCategoryId(categoryId);
        return repository.save(category);


    }
}


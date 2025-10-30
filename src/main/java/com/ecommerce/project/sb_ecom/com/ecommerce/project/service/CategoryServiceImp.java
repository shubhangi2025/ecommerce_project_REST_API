package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.exception.APIException;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder){
        Sort sortByANDOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        PageRequest pageDetails = PageRequest.of(pageNumber, pageSize, sortByANDOrder);
        //List<Category> categoryList = repository.findAll();
        Page<Category> categoryPage = repository.findAll(pageDetails);
        List<Category> categoryList = categoryPage.getContent();
        if(categoryList.isEmpty())
            throw new APIException("No categories available");

        List<CategoryDTO> categoryDTOs = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalpages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createNewCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = repository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException(category.getCategoryName() + " already available");
        }
        Category save = repository.save(category);
        return modelMapper.map(save, CategoryDTO.class);

    }
    @Override
    public CategoryDTO deleteCategoryById(Long categoryId) {

        Category category= repository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        repository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
        }

    @Override
    public CategoryDTO updateCategoryById(CategoryDTO categoryDTO, Long categoryId) {

        long savedCategoryId = repository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId)).getCategoryId();

        categoryDTO.setCategoryId(categoryId);
        Category mapCategory = modelMapper.map(categoryDTO, Category.class);
        Category updatedCategory = repository.save(mapCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);

    }
}


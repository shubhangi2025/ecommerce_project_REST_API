package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Product;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow( ()-> new ResourceNotFoundException("Category", "categoryId",categoryId));

        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        Product savedProduct = productRepository.save(product);
        ProductDTO mappedProduct = modelMapper.map(product, ProductDTO.class);
        return mappedProduct;
    }
}

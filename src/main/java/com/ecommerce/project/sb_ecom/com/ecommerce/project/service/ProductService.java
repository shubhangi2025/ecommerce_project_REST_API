package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Product;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.ProductDTO;
import org.springframework.stereotype.Service;


public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

}

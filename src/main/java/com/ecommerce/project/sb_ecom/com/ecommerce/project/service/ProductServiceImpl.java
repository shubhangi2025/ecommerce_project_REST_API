package com.ecommerce.project.sb_ecom.com.ecommerce.project.service;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Product;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.sb_ecom.com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        product.setProductName(product.getProductName());
        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        Product savedProduct = productRepository.save(product);
        ProductDTO mappedProduct = modelMapper.map(product, ProductDTO.class);
        return mappedProduct;
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOlist = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOlist);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
        List<Product> productsByCategoryId = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = productsByCategoryId.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;

    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
        List<Product> productsByKeyword = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productDTOList = productsByKeyword.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Product product, Long productId) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct = productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}

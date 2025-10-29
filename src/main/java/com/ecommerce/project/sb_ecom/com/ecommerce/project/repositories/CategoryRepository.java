package com.ecommerce.project.sb_ecom.com.ecommerce.project.repositories;

import com.ecommerce.project.sb_ecom.com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
}

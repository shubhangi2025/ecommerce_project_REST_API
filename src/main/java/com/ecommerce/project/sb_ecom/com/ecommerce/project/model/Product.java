package com.ecommerce.project.sb_ecom.com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank
    @Size(min = 3, message = "name must have atleast 3 characters")
    private String productName;

    @Size(min = 6, message = "name must have atleast 6 characters")
    private String description;
    private Integer quantity;
    private double price;
    private double specialPrice;
    private String image;
    private double discount;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

}

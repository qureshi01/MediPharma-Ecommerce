package com.example.dto;

import org.springframework.beans.BeanUtils;

import com.example.model.Product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductAddRequest {

    private String title;
    private String description;
    private int quantity;
    private double price;
    private int disc_price;
    private int categoryId;
    private String image;

    public static boolean validateProduct(ProductAddRequest request) {
        if (request.getTitle() == null || request.getDescription() == null || request.getPrice() == 0 ||
            request.getImage() == null || request.getQuantity() < 0 || request.getCategoryId() == 0) {
            return false;
        }

        // Additional validation logic if needed

        return true;
    }

    
    public static Product toEntity(ProductAddRequest dto) {
        if (dto == null) {
            return null;
        }
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}

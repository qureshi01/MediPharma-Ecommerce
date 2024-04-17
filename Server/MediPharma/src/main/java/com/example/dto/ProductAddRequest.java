package com.example.dto;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.example.model.Product;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductAddRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String title;
	private String description;
	private int quantity;
    private BigDecimal price;
    private int categoryId;
    private String image;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "ProductAddRequest [id=" + id + ", title=" + title + ", description=" + description + ", quantity="
				+ quantity + ", price=" + price + ", categoryId=" + categoryId + ", image=" + image + "]";
	}
	
	public static Product toEntity(ProductAddRequest dto) {
		Product entity=new Product();
		BeanUtils.copyProperties(dto, entity, "image", "categoryId");		
		return entity;
	}

	public static boolean validateProduct(ProductAddRequest request) {
        if (request.getTitle() == null || request.getDescription() == null || request.getPrice() == null ||
            request.getImage() == null || request.getQuantity() < 0 || request.getCategoryId() == 0) {
            return false;
        }

        // Additional validation logic if needed

        return true;
    }
    

}

package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductResponse extends CommonApiResponse {
	
	  	private int id;
	    private String title;
	    private String description;
	    private double price;
	    private int quantity;
	    private String imageName;
	
	private List<Product> products = new ArrayList<>();

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	

}

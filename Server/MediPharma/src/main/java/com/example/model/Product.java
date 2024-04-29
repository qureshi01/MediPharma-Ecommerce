package com.example.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
    private String title;
	private String description;
	private int quantity;
    private double price;
    private String image;
    private int disc_price;
    
    @ManyToOne
    @JoinColumn(name ="categoryId")
    private Category category;

}

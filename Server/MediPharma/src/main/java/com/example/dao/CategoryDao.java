package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {

}

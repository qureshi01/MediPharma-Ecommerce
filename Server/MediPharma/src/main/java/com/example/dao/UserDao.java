package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.UserResponse;
import com.example.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmailId(String emailId);
	

}

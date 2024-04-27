package com.example.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {


	User findByEmailId(String emailId);
	
	User getUserById(int userId);
		

}

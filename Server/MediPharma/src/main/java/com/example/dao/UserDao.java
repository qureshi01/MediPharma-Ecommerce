package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.UserResponse;
import com.example.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmailIdAndPasswordAndRole(String emailId, String password, String role);

	User findByEmailIdAndRole(String emailId, String role);

	List<User> findByRole(String role);

	User findByEmailId(String emailId);
	
	User getUserById(int userId);
	

}

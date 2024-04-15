package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	public List<User> getUsers(){
        //UserRepository userRepository = new UserRepository();
        List<User> users= repo.findAll();
        return users;
    }

}

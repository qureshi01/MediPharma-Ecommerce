package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.model.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class UserResponse extends CommonApiResponse{
	
	private String responseMessage;
    private boolean success;
    private String role;
	
	private List<User> users = new ArrayList<>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	

}

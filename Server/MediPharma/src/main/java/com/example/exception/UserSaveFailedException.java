package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserSaveFailedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserSaveFailedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	

	
}

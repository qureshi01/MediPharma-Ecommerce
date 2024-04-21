package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginRequest {
	
	private String emailId;
	private String password;
	private String role;

	private String newPassword;  // for forget password

	
	public static boolean validateLoginRequest(UserLoginRequest loginRequest) {
		if (loginRequest.getEmailId() == null || loginRequest.getPassword() == null || loginRequest.getRole() == null) {
			return false;
		}

		return true;
	}
	
	public static boolean validateForgetRequest(UserLoginRequest request) {
		if (request.getEmailId() == null || request.getPassword() == null || request.getNewPassword() == null) {
			return false;
		}

		return true;
	}

}

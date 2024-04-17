package com.example.dto;

public class UserLoginRequest {
	
	private String emailId;
	private String password;

	private String newPassword;  // for forget password

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "UserLoginRequest [emailId=" + emailId + ", password=" + password + ", newPassword="
				+ newPassword + "]";
	}
	
	public static boolean validateLoginRequest(UserLoginRequest loginRequest) {
		if (loginRequest.getEmailId() == null || loginRequest.getPassword() == null ) {
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

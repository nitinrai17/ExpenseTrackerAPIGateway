package com.example.technoxtream17.user.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ValidateRequest {
	
	@NotBlank
	private String token; 
	
	public ValidateRequest() {
		
	}
	
	public ValidateRequest(String token) {
		this.token=token; 
	}
	
	public String getToken() {
		return token;
	}
	
}

package com.example.technoxtream17.user.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;

}

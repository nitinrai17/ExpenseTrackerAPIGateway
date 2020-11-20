package com.example.technoxtream17.user.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateResponse {
	
	private Boolean success;
	
	private String message;
	
	private String accessToken;

}

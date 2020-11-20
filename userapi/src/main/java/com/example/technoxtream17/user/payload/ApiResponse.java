package com.example.technoxtream17.user.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
	
	private String message;
	private Boolean success; 

}

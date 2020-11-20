package com.example.technoxtream17.expensetracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
	
	private Object body;
	private Boolean success; 

}

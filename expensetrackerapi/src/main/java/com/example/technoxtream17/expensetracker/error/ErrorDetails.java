package com.example.technoxtream17.expensetracker.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {

	private Long errorCode;
	
	private String errorMessage;
	
	private String errorDescription; 
}

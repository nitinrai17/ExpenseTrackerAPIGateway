package com.example.technoxtream17.expensetracker.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GenericNotFoundException extends RuntimeException{
	
	public GenericNotFoundException(String message) {
		super(message);
	}

}

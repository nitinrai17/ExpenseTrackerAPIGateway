package com.example.technoxtream17.user.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class ResourceNotFoundExcpetion extends RuntimeException{

	private String resourceName;
	private String fieldName;
	private Object fieldValue;
	
	
	public ResourceNotFoundExcpetion(String resourceName, String fieldName , Object fieldValue ) {
		super(String.format("%s not found with %s : '%s'",resourceName, fieldName,fieldValue));
		this.resourceName=resourceName;
		this.fieldName= fieldName;
		this.fieldValue=fieldValue;
	}
	
}

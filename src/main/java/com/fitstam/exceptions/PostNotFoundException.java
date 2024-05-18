package com.fitstam.exceptions;

public class PostNotFoundException extends RuntimeException{
	
	String resourceName;
	String fieldName;
	long fieldValue;
	
	public PostNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not available with %s : %s",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}

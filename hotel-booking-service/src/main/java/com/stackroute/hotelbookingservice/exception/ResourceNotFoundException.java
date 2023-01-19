package com.stackroute.hotelbookingservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResourceNotFoundException extends RuntimeException {
	String fieldName;
	public ResourceNotFoundException(String fieldName){
		super(String.format("%s",fieldName));
		this.fieldName=fieldName;
	}
}



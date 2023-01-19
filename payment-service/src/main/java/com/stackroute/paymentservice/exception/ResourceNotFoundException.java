package com.stackroute.paymentservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResourceNotFoundException extends RuntimeException{
	
	String resourceName;
	String fieldName;
	int fieldValue;
}

package com.stackroute.hotelbookingservice.exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {
	@ExceptionHandler(ResourceNotFoundException.class)
	 String resuorceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message =ex.getMessage();
		return message;
		}
	@ExceptionHandler(BookingAlreadyExists.class)
	String bookingAlreadyExists(BookingAlreadyExists ex) {
		String message =ex.getMessage();
		return message;
	}


}

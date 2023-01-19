package com.stackroute.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ChatExceptionController {

	 @ExceptionHandler(value = ChatNotFoundException.class)
	    public ResponseEntity<Object> exception(ChatNotFoundException exception){
	        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}

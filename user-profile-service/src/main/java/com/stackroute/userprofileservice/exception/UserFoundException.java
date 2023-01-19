package com.stackroute.userprofileservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists")
public class UserFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserFoundException() {
        super("User with this email is already present !! try with another email");
    }

    public UserFoundException(String msg) {
        super(msg);
    }



}

package com.stackroute.userauthenticationservice.exception;

/**
 * this exception class is called when the user is trying to add already existing data to DB
 */
public class UserFoundException extends RuntimeException {

    public UserFoundException(String msg) {
        super(msg);
    }
}

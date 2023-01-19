package com.stackroute.chatservice.exception;

import java.util.NoSuchElementException;

public class ChatNotFoundException extends NoSuchElementException {
    public ChatNotFoundException(){

    }
    public ChatNotFoundException(String s){
        super(s);
    }
}

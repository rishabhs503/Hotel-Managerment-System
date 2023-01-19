package com.stackroute.hotelbookingservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingAlreadyExists extends RuntimeException {
    String fieldName;
    public BookingAlreadyExists(String fieldName){
        super(String.format("%s",fieldName));
        this.fieldName=fieldName;
    }
}

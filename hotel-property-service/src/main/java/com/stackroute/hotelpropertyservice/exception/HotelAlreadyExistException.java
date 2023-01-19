package com.stackroute.hotelpropertyservice.exception;

public class HotelAlreadyExistException extends Exception {
    public HotelAlreadyExistException(Integer id) {
        super("Hotel with id: " + id + " already exist");
    }
}

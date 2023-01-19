package com.stackroute.hotelpropertyservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No hotel found with this address")
public class NoHotelFoundWithThisAddress extends Exception{
}

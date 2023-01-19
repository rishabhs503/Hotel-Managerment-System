package com.stackroute.hotelpropertyservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "No hotel found with this name")
public class NoHotelFoundWithThisName extends Exception{
}

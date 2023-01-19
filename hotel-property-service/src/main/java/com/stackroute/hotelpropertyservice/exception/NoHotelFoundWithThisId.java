package com.stackroute.hotelpropertyservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No hotel found with this id")
public class NoHotelFoundWithThisId extends Exception {
}

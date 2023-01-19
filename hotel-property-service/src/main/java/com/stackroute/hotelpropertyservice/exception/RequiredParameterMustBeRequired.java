package com.stackroute.hotelpropertyservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NON_AUTHORITATIVE_INFORMATION, reason = "Required details must be valid")
public class RequiredParameterMustBeRequired extends Exception{
}

package com.stackroute.feedbackservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "provided data is not valid")
public class FoundInvalidData extends Exception {
}

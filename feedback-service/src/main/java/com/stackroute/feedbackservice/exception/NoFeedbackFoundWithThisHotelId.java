package com.stackroute.feedbackservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No feedbacks found for this hotel")
public class NoFeedbackFoundWithThisHotelId extends Exception{
}

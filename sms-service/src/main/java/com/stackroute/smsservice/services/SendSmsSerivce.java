package com.stackroute.smsservice.services;

import com.stackroute.smsservice.model.UserDetails;
import org.springframework.http.ResponseEntity;

public interface SendSmsSerivce {
    ResponseEntity<String> sendSMS(UserDetails userDetails);
}

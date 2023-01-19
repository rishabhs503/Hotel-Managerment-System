package com.stackroute.smsservice.services;

import com.stackroute.smsservice.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@org.springframework.stereotype.Service
public class SmsService {
    private final SendSmsSerivce sendSmsSerivce;
    @Autowired
    public SmsService(SendSmsSerivce sendSmsSerivce) {
        this.sendSmsSerivce = sendSmsSerivce;
    }

    public ResponseEntity<String> sendSms(UserDetails userDetails) {
        return sendSmsSerivce.sendSMS(userDetails);
    }
}

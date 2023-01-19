package com.stackroute.emailservice.service;

import com.stackroute.emailservice.exception.EmailException;
import com.stackroute.emailservice.model.UserDetails;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<String> welcomeMail(UserDetails userDetail) throws EmailException;

    ResponseEntity<String> bookingMail(UserDetails bookingDetails) throws EmailException;

    ResponseEntity<String> otpMail(UserDetails details) throws EmailException;

    ResponseEntity<String> forgetMail(UserDetails details) throws EmailException;

    ResponseEntity<String> resetSuccessMail(UserDetails details) throws EmailException;

    ResponseEntity<String> daulFactorAuth(UserDetails details) throws EmailException;

    ResponseEntity<String> unSubMail(UserDetails details) throws EmailException;

    String dataOfferMail(UserDetails userDetails);

    ResponseEntity<String> cancellationMail(UserDetails details) throws EmailException;

}

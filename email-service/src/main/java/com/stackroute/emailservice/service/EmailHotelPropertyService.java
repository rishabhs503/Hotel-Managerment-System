package com.stackroute.emailservice.service;

import com.stackroute.emailservice.exception.EmailException;
import com.stackroute.emailservice.model.HotelPropertyDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmailHotelPropertyService {

    ResponseEntity<String> newHotelMail(HotelPropertyDetails details) throws EmailException;
    ResponseEntity<String> updateHotelMail(HotelPropertyDetails details) throws EmailException;
    ResponseEntity<String> deleteHotelMail(HotelPropertyDetails details) throws EmailException;

}

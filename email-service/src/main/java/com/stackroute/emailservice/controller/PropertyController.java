package com.stackroute.emailservice.controller;


import com.stackroute.emailservice.exception.EmailException;
import com.stackroute.emailservice.model.HotelPropertyDetails;
import com.stackroute.emailservice.service.EmailHotelPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private EmailHotelPropertyService propertyService;

    @PostMapping("/addhotel")
    public ResponseEntity<String> mailForNewHotel(@RequestBody HotelPropertyDetails propertyDetails) throws EmailException {

        ResponseEntity<String> response = propertyService.newHotelMail(propertyDetails);
        return response;
    }

    @PostMapping("/udpatehotel")
    public ResponseEntity<String> mailForUpdateHotel(@RequestBody HotelPropertyDetails propertyDetails) throws EmailException {

        ResponseEntity<String> response = propertyService.updateHotelMail(propertyDetails);
        return response;
    }

    @PostMapping("/deletehotel")
    public ResponseEntity<String> mailForDelete(@RequestBody HotelPropertyDetails propertyDetails) throws EmailException {

        ResponseEntity<String> response = propertyService.deleteHotelMail(propertyDetails);
        return response;
    }
}

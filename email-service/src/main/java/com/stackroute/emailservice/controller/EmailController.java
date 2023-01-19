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

import com.stackroute.emailservice.model.UserDetails;
import com.stackroute.emailservice.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailServ;
    @Autowired
    private EmailHotelPropertyService hotelService;

    /**
     * This End point is for Sending an welcome email after successful registration and verification
     * @param userDetails
     */
    @PostMapping("/welcome")
    public ResponseEntity<String> welcomeMail(@RequestBody UserDetails userDetails) throws EmailException {

        ResponseEntity<String> response = emailServ.welcomeMail(userDetails);
        return response;
    }

    /**
     * This End point is for Sending OTP after Successful Registration
     * @param details
     */
    @PostMapping("/otpmail")
    public ResponseEntity<String> sendOtp(@RequestBody UserDetails details) throws EmailException {

        ResponseEntity<String> response = emailServ.otpMail(details);
        return response;
    }

    /**
     * This end point is used for sending verification mail having OTP for changing password
     * @param details
     */
    @PostMapping("/forgetmail")
    public ResponseEntity<String> forgetMail(@RequestBody UserDetails details) throws EmailException {

        ResponseEntity<String> response = emailServ.forgetMail(details);
        return response;
    }

    /**
     * This End point is for Sending a mail of Alerting/Informing about the recent Password Update.
     * @param details
     */
    @PostMapping("/passupdated")
    public ResponseEntity<String> resetSuccess(@RequestBody UserDetails details) throws EmailException {

        ResponseEntity<String> response = emailServ.resetSuccessMail(details);
        return response;
    }

    /**
     * @param details
     */
    @PostMapping("/unsub")
    public ResponseEntity<String> unsubMail(@RequestBody UserDetails details) throws EmailException {

        ResponseEntity<String> response = emailServ.unSubMail(details);
        return response;
    }

    /**
     * This end point is for Sending Offers alert mail to Many users at once.
     * @param userDetails
     */
    @PostMapping("/offer")
    public String offerMail(@RequestBody UserDetails userDetails) {

        return emailServ.dataOfferMail(userDetails);
    }

    /**
     * This End point is used for Sending Dual Authentication OTP to the user
     * @param details
     */
    @PostMapping("/dualauth")
    public ResponseEntity<String> loginAuth(@RequestBody UserDetails details) throws EmailException {

        ResponseEntity<String> response = emailServ.daulFactorAuth(details);
        return response;
    }

    // METHOD HANDLERS For BOOKING SERVICE--------------------------------------------------------------

    /**
     * This End point is for Sending Booking Detail and invoice to user
     * @param bookingDetails
     */
    @PostMapping("/bookingdetail")
    public ResponseEntity<String> bookingData(@RequestBody UserDetails bookingDetails) throws EmailException {

        ResponseEntity<String> response = emailServ.bookingMail(bookingDetails);
        return response;
    }

    /**
     * This End Point is used for Sending Mail of Informing about the Booking Cancellation.
     * @param details
     */
    @PostMapping("/cancellation")
    public ResponseEntity<String> bookingCancel(@RequestBody UserDetails details) throws EmailException {

        ResponseEntity<String> response = emailServ.cancellationMail(details);
        return response;
    }

// METHOD HANDLERS For HOTEL PROPERTY SERVICE ----------------------------
    @PostMapping("/newhotel")
    public ResponseEntity<String> addNewHotel(@RequestBody HotelPropertyDetails details) throws EmailException {

        ResponseEntity<String> response = hotelService.newHotelMail(details);
        return response;
    }
    @PostMapping("/updatehotel")
    public ResponseEntity<String> updateHotel(@RequestBody HotelPropertyDetails details) throws EmailException {

        ResponseEntity<String> response = hotelService.updateHotelMail(details);
        return response;
    }
    @PostMapping("/deletehotel")
    public ResponseEntity<String> deleteHotel(@RequestBody HotelPropertyDetails details) throws EmailException {

        ResponseEntity<String> response = hotelService.deleteHotelMail(details);
        return response;
    }


}

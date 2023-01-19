package com.stackroute.smsservice.services;

import com.stackroute.smsservice.model.MessageTemplate;
import com.stackroute.smsservice.model.UserDetails;
import com.stackroute.smsservice.config.TwilioConfigureDetails;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SendSmsServiceImpl implements SendSmsSerivce {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendSmsServiceImpl.class);

    private final TwilioConfigureDetails twilioConfigureDetails;

    private final MessageTemplate messageTemplate;

    @Autowired
    public SendSmsServiceImpl(TwilioConfigureDetails twilioConfigureDetails, MessageTemplate messageTemplate) {
        this.twilioConfigureDetails = twilioConfigureDetails;
        this.messageTemplate = messageTemplate;
    }

    public ResponseEntity<String> sendSMS(UserDetails userDetails) {

        String message = getMessageTemplateBasedOnUserRole(userDetails);
        PhoneNumber to = new PhoneNumber(userDetails.getNumbers());
        PhoneNumber from = new PhoneNumber(twilioConfigureDetails.getSmsSenderNumber());

        try {
            Twilio.init(
                    twilioConfigureDetails.getTwilioAccountSid(),
                    twilioConfigureDetails.getTwilioAuthToken()
            );

            MessageCreator creator = Message.creator(to, from, message);
            Message messageT = creator.create();
            LOGGER.info("Message details : {}", messageT);
        } catch (Exception e) {
            LOGGER.info("Exception occurs : {}", e.getMessage());
        }
        return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
    }


    private String getMessageTemplateBasedOnUserRole(UserDetails userDetails) {
        String replyMessagetemplate = null;

        switch(userDetails.getRequestType()){
            case "forget-password":
                replyMessagetemplate = messageTemplate.getForgetPasswordMessageTemplate().replace("[username]", userDetails.getFirstname())
                        .replace("[OTP]", userDetails.getDetails().getOpt())
                        .replace("[URL]", userDetails.getDetails().getUrl());
                break;
            case "offers":
                replyMessagetemplate = messageTemplate.getOffersMessageTemplate().replace("[username]", userDetails.getFirstname())
                        .replace("[offer message]",userDetails.getDetails().getOfferMessage());
                break;
            case "booking-payment-success":
                replyMessagetemplate = messageTemplate.getSuccessBookingMessageTemplate().replace("[username]",userDetails.getFirstname())
                        .replace("[booking-id]", String.valueOf(userDetails.getDetails().getBookingId()))
                        .replace("[room-id]",userDetails.getDetails().getRoomId())
                        .replace("[date-time-checkin]",userDetails.getDetails().getCheckIn())
                        .replace("[date-time-checkout]", userDetails.getDetails().getCheckout())
                        .replace("[room-type]", userDetails.getDetails().getRoomType())
                        .replace("[no-of-person]", String.valueOf(userDetails.getDetails().getNumberPerson()))
                        .replace("[total-price]", String.valueOf(userDetails.getDetails().getPrice()))
                        .replace("[hotel-name]",userDetails.getDetails().getHotelName());
                break;
            case "success-verified-user":
                replyMessagetemplate = messageTemplate.getMobileNumberVerifiedMessageTemplate().replace("[username]", userDetails.getFirstname())
                        .replace("[OTP]", userDetails.getDetails().getOpt())
                        .replace("[URL]", userDetails.getDetails().getUrl());
                break;
            case "register-property-success":
                replyMessagetemplate = messageTemplate.getSuccessRegisterHotel().replace("[username]", userDetails.getFirstname())
                        .replace("[hotel-name]", userDetails.getDetails().getHotelName());
                break;
            default:
                LOGGER.warn("invalid request type {0}", userDetails.getRequestType());

        }
        return replyMessagetemplate;
    }
}


package com.stackroute.smsservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class MessageTemplate {

    // Message Teamplates
    @Value("${message.user.forget-password}")
    private String forgetPasswordMessageTemplate;

    @Value("${message.user.mobile-number-verified}")
    private String mobileNumberVerifiedMessageTemplate;

    @Value("${message.user.offers}")
    private String offersMessageTemplate;

    @Value("${message.user.success-booking}")
    private String successBookingMessageTemplate;

    @Value("${message.hotel.success-register}")
    private String successRegisterHotel;

}

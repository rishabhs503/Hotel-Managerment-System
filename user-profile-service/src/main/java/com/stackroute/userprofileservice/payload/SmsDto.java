package com.stackroute.userprofileservice.payload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsDto {

    private int userId;
    private String firstName;
    private String mobileNumber;
    private String otp;
    private int port;

}

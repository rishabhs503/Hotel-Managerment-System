package com.stackroute.smsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserForgetPasswordDto {
    private String firstName;
    private String mobileNumber;
    private String otp;
    // used in url
    private int port;
}

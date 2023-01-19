package com.stackroute.smsservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserVerficationDto {

    // user unique key
    private int userId;

    private String firstName;
    private String mobileNumber;
    private String otp;

    // used in url
    private int port;

}

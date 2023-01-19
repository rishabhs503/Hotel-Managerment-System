package com.stackroute.userprofileservice.payload;


import lombok.*;

@Getter
@Setter
public class EmailDto {

    private int userId;
    private String firstName;
    private String email;
    private String otp;
    private int port;


}

package com.stackroute.hotelpropertyservice.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Integer userId;
    private String firstName;
    private String mobileNumber;
    private String emailId;
}
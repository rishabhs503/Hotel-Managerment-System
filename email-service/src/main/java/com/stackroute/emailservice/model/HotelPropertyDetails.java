package com.stackroute.emailservice.model;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelPropertyDetails {

    private String hotelName;
    @Email
    private String emailID;
    private String firstName;

}

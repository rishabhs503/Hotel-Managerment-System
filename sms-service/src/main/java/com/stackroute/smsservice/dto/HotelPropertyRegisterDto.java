package com.stackroute.smsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class HotelPropertyRegisterDto {
    private String hotelName;
    private String firstName;
    private String contact_number;
}

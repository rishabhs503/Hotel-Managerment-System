package com.stackroute.hotelbookingservice.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HotelAddressDto {
    private String addressId;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}

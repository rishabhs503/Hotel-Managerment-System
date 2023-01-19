package com.stackroute.hotelpropertyservice.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmsDto {

    private String hotelName;
    private String firstName;
    private String contact_number;

}

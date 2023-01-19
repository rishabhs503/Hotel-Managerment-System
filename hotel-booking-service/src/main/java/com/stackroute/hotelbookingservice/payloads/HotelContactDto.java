package com.stackroute.hotelbookingservice.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HotelContactDto {
    private  Long phoneNumber;
    private String email;

}

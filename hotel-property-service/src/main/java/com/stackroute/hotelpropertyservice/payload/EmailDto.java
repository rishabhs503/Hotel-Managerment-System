package com.stackroute.hotelpropertyservice.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDto {

    private String hotelName;
    private String firstName;
    private String email;
}

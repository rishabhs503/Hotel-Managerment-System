package com.stackroute.hotelpropertyservice.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RatingsDto {
    private Double hospitality;
    private Double valueformoney;
    private Double location;
    private Double facilities;
    private Double cleanliness;
}

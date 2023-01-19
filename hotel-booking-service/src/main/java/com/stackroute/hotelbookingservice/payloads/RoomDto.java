package com.stackroute.hotelbookingservice.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDto {
    private String roomType;
    private double price;
    private int noOfRooms;
}

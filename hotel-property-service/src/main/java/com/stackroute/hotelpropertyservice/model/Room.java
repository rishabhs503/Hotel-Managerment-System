package com.stackroute.hotelpropertyservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Room {
    private String roomType;
    private int noOfRooms;
    private double price;
}

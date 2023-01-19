package com.stackroute.hotelbookingservice.payloads;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailDTO {
    private String email;
    private String firstName;
    private int bookingId;
    private String roomType;
    private String hotelName;
    private String checkIn;
    private  String checkOut;
    private int noOfDays;
    private  String address;
    private String hotelReceptionNumber;
    private double totalPrice;
}
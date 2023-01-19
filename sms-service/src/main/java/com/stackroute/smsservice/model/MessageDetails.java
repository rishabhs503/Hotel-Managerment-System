package com.stackroute.smsservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDetails {

    private String url;

    private String opt;

    private String offerMessage;

    private int bookingId;

    private String roomId;

    private String checkIn;

    private String checkout;

    private String roomType;

    private int numberPerson;

    private double price;

    private String hotelName;

    private int noOfdays;

}

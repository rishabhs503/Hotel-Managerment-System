package com.stackroute.emailservice.model;

import java.util.List;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {
    @Email
    private String recieverMailAdd;

    private String firstName;

    private List<String> usersMailIds;
    private List<String> usersfirstName;
    private String subject;


    private int orderId;
    private double totalPrice;

    private String hotelName;
    private String htmlBody;
    private String link;

    private String otp;
    private int noOfDays;
    private String checkIn;
    private String checkOut;
    private String address;
    private String hotelReceptionNumber;
    private String roomType;

}

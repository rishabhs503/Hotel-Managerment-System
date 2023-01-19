package com.stackroute.hotelbookingservice.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SmsDTO {
        private String userName;
        private String contactNumber;
        private String roomId;
        private int bookingId;
        private String hotelName;
        private String checkInDate;
        private String checkOutDate;
        private String roomType;
        private int numberOfGuests;
        private int noOfDays;
        private double totalPrice;
    }


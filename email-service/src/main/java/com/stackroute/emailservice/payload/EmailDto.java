package com.stackroute.emailservice.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDto {

	private int userId;
	private String email;
	private String otp;
	private int port;
	private String firstName;

	private int bookingId;
	private String roomType;
	private String hotelName;
	private String checkIn;
	private String checkOut;
	private String address;
	private String hotelReceptionNumber;
	private double totalPrice;

	private int noOfDays;




}

package com.stackroute.hotelbookingservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(collection="booking_Details")
public class Booking {
	@Id
	private int bookingId;
	private String checkInDate;
	private String checkInTime;
	private String checkOutDate;
	private String  checkOutTime;
	private int   numberOfGuests;
	private double priceOfRoom;
	private String hotelName;
	private  String address;
	private String hotelReceptionNumber;
	private String roomType;
	private int noOfDays;
	private int noOfRooms;
	private double totalPrice;
	private String userName;
	private String emailId;
	private String contactNumber;
	private String paymentId;
	private String orderId;
	private String paymentSignature;
	private boolean bookingConfirmed;

}
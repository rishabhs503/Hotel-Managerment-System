package com.stackroute.hotelbookingservice.payloads;


import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {
	private int bookingId;
	private String userName;
	private String emailId;
	private String contactNumber;
	private double totalPrice;

}

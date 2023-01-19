package com.stackroute.hotelbookingservice.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentReceiverDTO {
	private int bookingId;
	private String paymentId;
	private String orderId;
	private String paymentSignature;

}

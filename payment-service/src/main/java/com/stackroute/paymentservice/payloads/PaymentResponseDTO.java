package com.stackroute.paymentservice.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentResponseDTO {
    int bookingId;
    String paymentId;
    String orderId;
    String paymentSignature;
}

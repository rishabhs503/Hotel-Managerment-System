package com.stackroute.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection="order_Details")
public class OrderRequest {

	@Id
	private int orderId;
	private String customerName;
	private String email;
	private String phoneNumber;
	private double amount;
}

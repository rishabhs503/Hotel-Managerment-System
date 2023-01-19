package com.stackroute.paymentservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PaymentServiceApplicationTests {

	@Test
	void contextLoads() {
		PaymentServiceApplication.main(new String[]{});
		assertTrue(true);
	}

}

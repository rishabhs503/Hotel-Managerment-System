package com.stackroute.hotelpropertyservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class HotelPropertyServiceApplicationTests {

	@Test
	void contextLoads() {
		HotelPropertyServiceApplication.main(new String[]{});
		Assertions.assertTrue(true);
	}

}

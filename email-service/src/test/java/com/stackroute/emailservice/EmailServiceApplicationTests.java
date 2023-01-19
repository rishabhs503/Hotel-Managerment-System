package com.stackroute.emailservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmailServiceApplicationTests {

	@Test
	void contextLoads() {
		EmailServiceApplication.main(new String[]{});
		assertTrue(true);
	}

}

package com.stackroute.userauthenticationservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserAuthenticationServiceApplicationTests {

	@Test
	void contextLoads() {
		UserAuthenticationServiceApplication.main(new String[]{});
		assertTrue(true);
	}

}

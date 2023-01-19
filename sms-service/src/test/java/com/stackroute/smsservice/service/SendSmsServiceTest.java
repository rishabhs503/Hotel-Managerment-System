package com.stackroute.smsservice.service;

import com.stackroute.smsservice.model.MessageDetails;
import com.stackroute.smsservice.model.UserDetails;
import com.stackroute.smsservice.services.SendSmsSerivce;
import com.stackroute.smsservice.services.SmsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SendSmsServiceTest {

    private UserDetails userDetails1, userDetails2;
    private MessageDetails messageDetails1, messageDetails2;

    @Mock
    SendSmsSerivce sendSmsSerivce;

    @InjectMocks
    SmsService smsService;

    @BeforeEach
    public void setUp() {
        // Added user details - 1
        userDetails1 = new UserDetails();
        userDetails1.setNumbers("+9188600827");
        userDetails1.setRole("user");
        userDetails1.setFirstname("vishal");
        userDetails1.setRequestType("reset");
        userDetails1.setLastname("topibaaz");
        // set message details in user details
        userDetails1.setDetails(messageDetails1);

        // create a object of message details
        messageDetails1 = new MessageDetails();
        messageDetails1.setOpt("223212");

        // Added user details - 2
        userDetails2 = new UserDetails();
        userDetails2.setNumbers("+918860082597");
        userDetails2.setRole("user");
        userDetails2.setFirstname("vishal");
        userDetails2.setRequestType("reset");
        userDetails2.setLastname("topibaaz");
        // set message details in user details
        userDetails2.setDetails(messageDetails2);

        // create a object of message details
        messageDetails2 = new MessageDetails();
        messageDetails2.setOpt("446567");
    }

    @AfterEach
    public void tearDown() {
        userDetails1 = null;
        userDetails2 = null;
        messageDetails1 = null;
        messageDetails2 = null;
    }

    @Test
    void testSendSms(){
        Mockito.when(sendSmsSerivce.sendSMS(Mockito.any())).thenReturn(new ResponseEntity<>("Invalid mobile number", HttpStatus.BAD_REQUEST)); // <-- Add this line
        ResponseEntity<String> test = smsService.sendSms(userDetails1);
        assertNotNull(test);
        assertEquals("Invalid mobile number",test.getBody());
    }



}

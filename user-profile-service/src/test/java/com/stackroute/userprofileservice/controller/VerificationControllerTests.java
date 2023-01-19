package com.stackroute.userprofileservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userprofileservice.models.Address;
import com.stackroute.userprofileservice.models.Users;
import com.stackroute.userprofileservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class VerificationControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Mock
    UserService userService;

    @InjectMocks
    VerificationController verificationController;


    Users user1;
    Address address1;



    @BeforeEach
    public void setUp() {



        user1 = new Users(111 ,"Swastika","Shanker","9561245882",address1,"OWNER","swas.123@gmail.com","2022-11-24","1998-11-02","F","b5EA228N",true,"swas@123");
        address1 = new Address("Patna","Bihar","India","800014");

        mockMvc = MockMvcBuilders.standaloneSetup(verificationController).build();



    }

    @AfterEach
    public void tearDown() {
        user1= null;
        address1 = null;
    }


    @Test
    public void verifyOtp()throws Exception{

        when(userService.verifyOtp(anyInt(),any())).thenReturn("Otp verified.Registration is Successful.");
        mockMvc.perform(post("/verification/otp/111/b5EA228N")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).verifyOtp(anyInt(),any());
    }




    @Test
    public void forgotPasswordByEmail()throws Exception{
        when(userService.forgotPasswordByEmail(anyString())).thenReturn(user1.getEmailId());
        mockMvc.perform(post("/verification/forgot-password-by-email/swas.123@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).forgotPasswordByEmail(anyString());
    }

    @Test
    public void forgotPasswordByMobile()throws Exception{
        when(userService.forgotPasswordByMobile(anyString())).thenReturn(user1.getMobileNumber());
        mockMvc.perform(post("/verification/forgot-password-by-mobile/9561245882")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).forgotPasswordByMobile(anyString());
    }




    private static String jsonToString(final Object o) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(o);
            result = jsonContent;
            return result;

        } catch (JsonProcessingException e) {
            result = "JsonProcessingException";
        }
        return result;
    }







}

package com.stackroute.userauthenticationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userauthenticationservice.entity.JwtRequest;
import com.stackroute.userauthenticationservice.service.UserAuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserAuthenticationService jwtService;

    @InjectMocks
    private UserAuthenticationController userAuthenticationController;

    String token;
    JwtRequest jwtRequest;
    ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        jwtRequest = new JwtRequest();
        jwtRequest.setEmailId("abc.12@gmail.com");
        jwtRequest.setPassword("password#543!");
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        mockMvc = MockMvcBuilders.standaloneSetup(userAuthenticationController).build();
    }

    @AfterEach
    public void tearDown() {
        jwtRequest = null;
        token = null;
    }

    @Test
    public void createJwtTokenTest() throws Exception {
        when(jwtService.createJwtToken(any())).thenReturn(token);

        mockMvc.perform(post("/authenticate/generateToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(jwtService, times(1)).createJwtToken(any());
    }

    @Test
    public void createJwtTokenTestFailure() throws Exception {
        when(jwtService.createJwtToken(any())).thenThrow(UsernameNotFoundException.class);

        mockMvc.perform(post("/authenticate/generateToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isForbidden())
                .andDo(print());

        verify(jwtService, times(1)).createJwtToken(any());
    }
}

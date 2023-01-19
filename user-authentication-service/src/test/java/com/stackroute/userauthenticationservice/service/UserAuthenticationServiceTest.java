package com.stackroute.userauthenticationservice.service;

import com.stackroute.userauthenticationservice.entity.*;
import com.stackroute.userauthenticationservice.repository.UserCredentialsRepository;

import com.stackroute.userauthenticationservice.service.impl.UserAuthenticationServiceImpl;
import com.stackroute.userauthenticationservice.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserAuthenticationServiceTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @InjectMocks
    private UserAuthenticationServiceImpl userAuthenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserCredentials userCredentials;
    User user;
    JwtRequest jwtRequest;
    String token;

    @BeforeEach
    public void setUp() {
        userCredentials = new UserCredentials();
        userCredentials.setEmailId("abc.12@gmail.com");
        userCredentials.setPassword(getEncodedPassword("password#543!"));
        userCredentials.setRole(new Role(1, "OWNER"));

        user = new User(userCredentials.getEmailId(), userCredentials.getPassword(), userAuthenticationService.getAuthorities(userCredentials));

        jwtRequest = new JwtRequest();
        jwtRequest.setEmailId("abc.12@gmail.com");
        jwtRequest.setPassword("password#543!");

        token = "";
    }

    @AfterEach
    public void tearDown() {
        userCredentials = null;
        user = null;
        jwtRequest = null;
        token = null;
    }

    @Test
    void loadUserByUsernameTest() throws UsernameNotFoundException {
        when(userCredentialsRepository.findById(userCredentials.getEmailId())).thenReturn(Optional.ofNullable(userCredentials));
        assertEquals(user, userAuthenticationService.loadUserByUsername(userCredentials.getEmailId()));
        verify(userCredentialsRepository, times(1)).findById(anyString());
    }

    @Test
    void loadUserByUsernameTestFailure() throws UsernameNotFoundException {
        when(userCredentialsRepository.findById(userCredentials.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userAuthenticationService.loadUserByUsername(userCredentials.getEmailId()));
        verify(userCredentialsRepository, times(1)).findById(anyString());
    }

    @Test
    void createJwtTokenTest() throws Exception {
        when(userCredentialsRepository.findById(userCredentials.getEmailId())).thenReturn(Optional.of(userCredentials));
        when(jwtUtil.generateToken(userCredentials)).thenReturn(token);
        assertEquals(token, userAuthenticationService.createJwtToken(jwtRequest));
        verify(userCredentialsRepository, times(1)).findById(anyString());
    }

    @Test
    void createJwtTokenTestFailureOne() throws Exception {
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getEmailId(), userCredentials.getPassword()))).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> userAuthenticationService.createJwtToken(jwtRequest));
        verify(userCredentialsRepository, times(1)).findById(anyString());
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}

package com.stackroute.userauthenticationservice.controller;

import com.stackroute.userauthenticationservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * This controller is used for testing the valid JWT token
 */
@RestController
@RequestMapping("/test")
public class JwtTokenTest {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/jwtTokenForOwner")
    public String forOwner(HttpServletRequest request) {
        // extracting the role
        final String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String role = (String) jwtUtil.extractRole(token);
        String mail = jwtUtil.getUserNameFromToken(token);
        return "Hi!, welcome " + mail + " your role is: " + role;
    }

    @GetMapping("/jwtTokenForCustomer")
    public String forCustomer(HttpServletRequest request) {
        // extracting the role
        final String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String role = (String) jwtUtil.extractRole(token);
        String mail = jwtUtil.getUserNameFromToken(token);
        return "Hi!, welcome " + mail + " your role is: " + role;
    }

}

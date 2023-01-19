package com.stackroute.userauthenticationservice.controller;


import com.stackroute.userauthenticationservice.entity.JwtRequest;
import com.stackroute.userauthenticationservice.entity.Role;
import com.stackroute.userauthenticationservice.service.RoleService;
import com.stackroute.userauthenticationservice.service.UserAuthenticationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this controller provides an API which is used to generate the JWT token
 */
@ApiOperation(value = "/authenticate", tags = "User Authentication Controller")
@RestController
@RequestMapping("/authenticate")
public class UserAuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private RoleService roleService;

    /**
     * this method is called as soon as spring boot application is build and is used to add two roles
     */
    @PostConstruct
    public void initRolesAndUser() {
        // creating the list to add roles
        List<Role> roles = Stream.of(
                new Role(1, "OWNER"), new Role(2, "CUSTOMER")
        ).collect(Collectors.toList());
        roleService.initRoles(roles);
    }

    /**
     * This method authenticate the emailId and password along with the OTP and generates the token
     *
     * @param jwtRequest
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "Authenticate user and generate token", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = String.class),
            @ApiResponse(code = 403, message = "FORBIDDEN !!", response = String.class),
    })
    @PostMapping("/generateToken")
    public ResponseEntity<String> createJwtToken(@RequestBody JwtRequest jwtRequest) {
        try {
            String token = userAuthenticationService.createJwtToken(jwtRequest);
            return new ResponseEntity<>("Token is following:\nBearer " + token, HttpStatus.OK);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return new ResponseEntity<>(usernameNotFoundException.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}

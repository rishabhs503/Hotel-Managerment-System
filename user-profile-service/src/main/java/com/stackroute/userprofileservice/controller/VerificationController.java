package com.stackroute.userprofileservice.controller;



import com.stackroute.userprofileservice.exception.UserNotFoundException;
import com.stackroute.userprofileservice.models.Users;
import com.stackroute.userprofileservice.service.UserService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


@ApiOperation(value = "/verification", tags = "User Registration Controller")
@RestController
@RequestMapping("/verification")
public class VerificationController {


    @Autowired
    private UserService userService;


    /**
     *This endpoint is used for dual factor authentication of the User while registration.
     * After successful registration it sends User data to authentication service.
     * @author Swastika Shanker
     */
    @ApiOperation(value = "To verify the otp and register successfully", response = String.class)
    @PostMapping("/otp/{userId}/{otp}")
    public String verifyOTP(@PathVariable("userId") int id,@PathVariable("otp") String otp ) {

        return userService.verifyOtp(id,otp);
    }

    /**
     * This endpoint is used when a user clicks on "Forgot password".
     * Unique otp is generated for valid user emailId.
     * @author Swastika Shanker
     */
    @ApiOperation(value = "To generate otp in case the user forgets password.", response = String.class)
    @PostMapping("/forgot-password-by-email/{emailId}")
    public String resetPasswordByEmail(@PathVariable("emailId") String emailId) throws UserNotFoundException {
        return userService.forgotPasswordByEmail(emailId);
    }

    /**
     * This endpoint is used when a user clicks on "Forgot password".
     * Unique otp is generated for valid user emailId.
     * @author Swastika Shanker
     */
    @ApiOperation(value = "To generate otp in case the user forgets password.", response = String.class)
    @PostMapping("/forgot-password-by-mobile/{mobileNumber}")
    public String resetPasswordByMobile(@PathVariable("mobileNumber") String mobileNumber) throws UserNotFoundException {
        return userService.forgotPasswordByMobile(mobileNumber);
    }




    /**
     * This endpoint is used to verify the otp generated to reset the password.
     *  @author Swastika Shanker
     */
    @ApiOperation(value = "To reset password for the user using otp in case the user forgets password.", response = String.class)
    @PutMapping("/reset-password/{userId}/{otp}")
    public String resetForVerifiedUser(@RequestBody Users user,@PathVariable("userId") int id,@PathVariable("otp") String otp){
           return userService.updatePassword(user,id,otp);
    }



}





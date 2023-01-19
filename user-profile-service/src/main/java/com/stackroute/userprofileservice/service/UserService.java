package com.stackroute.userprofileservice.service;



import com.stackroute.userprofileservice.exception.UserFoundException;
import com.stackroute.userprofileservice.exception.UserNotFoundException;
import com.stackroute.userprofileservice.models.Users;

import java.util.List;


public interface UserService {
    Users saveUser(Users user)throws UserFoundException;
    Boolean deleteUser(int userId) throws UserNotFoundException;
    List<Users> getAllUsers() throws UserNotFoundException;
    Users getUserById (int id);

    Users updateUser(Users user, int userId);
    
    String updatePassword(Users user, int userId,String otp);


    String verifyOtp(int id, String num1);

    String forgotPasswordByEmail(String emailId);

   String forgotPasswordByMobile(String mobileNumber);
}

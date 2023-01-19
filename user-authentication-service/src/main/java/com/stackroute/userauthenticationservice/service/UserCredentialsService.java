package com.stackroute.userauthenticationservice.service;

import com.stackroute.userauthenticationservice.entity.UserCredentials;

public interface UserCredentialsService {
    UserCredentials saveUser(UserCredentials userCredentials);

    UserCredentials updateUser(UserCredentials userCredentials, String emailId);

    boolean deleteUser(String emailId);
}

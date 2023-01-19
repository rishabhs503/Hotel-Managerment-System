package com.stackroute.userauthenticationservice.service.impl;

import com.stackroute.userauthenticationservice.entity.UserCredentials;
import com.stackroute.userauthenticationservice.exception.UserFoundException;
import com.stackroute.userauthenticationservice.exception.UserNotFoundException;
import com.stackroute.userauthenticationservice.repository.UserCredentialsRepository;
import com.stackroute.userauthenticationservice.service.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserCredentialsServiceImpl implements UserCredentialsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserCredentials saveUser(UserCredentials userCredentials) {
        if (userCredentialsRepository.findById(userCredentials.getEmailId()).isPresent()) {
            throw new UserFoundException("User with email: " + userCredentials.getEmailId() + " already present !!");
        } else {
            return userCredentialsRepository.save(userCredentials);
        }
    }

    @Override
    public UserCredentials updateUser(UserCredentials userCredentials, String emailId) {
        if (userCredentialsRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException("No user with email: " + emailId + " found !!");
        }
        return userCredentialsRepository.save(userCredentials);
    }

    @Override
    public boolean deleteUser(String emailId) {
        if (userCredentialsRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException("No user with email: " + emailId + " found !!");
        } else {
            userCredentialsRepository.deleteById(emailId);
            return true;
        }
    }
}

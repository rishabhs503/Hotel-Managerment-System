package com.stackroute.userauthenticationservice.service;

import com.stackroute.userauthenticationservice.entity.UserCredentials;
import com.stackroute.userauthenticationservice.exception.UserFoundException;
import com.stackroute.userauthenticationservice.payload.UserDto;
import com.stackroute.userauthenticationservice.repository.RoleRepository;
import lombok.Generated;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class is responsible to receive the payload via RabbitMq and perform CRUD operation to the PostgreSQL DB
 */
@Component
@Generated
public class RabbitConsumer {

    /**
     * creating the instance of repository using autowired annotation
     */
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private static final String QUEUE_TO_SAVE_USER = "queue_to_save_user";
    private static final String QUEUE_TO_UPDATE_USER = "queue_to_update_user";
    private static final String QUEUE_TO_DELETE_USER = "queue_to_delete_user";

    /**
     * This method receive the object from the queue of RabbitMq and then convert it into user credential and save to DB
     *
     * @param userDto
     */
    @RabbitListener(queues = QUEUE_TO_SAVE_USER)
    public void getDataFromRabbitMqToSaveUser(UserDto userDto) throws UserFoundException {
        UserCredentials user = new UserCredentials();
        user.setEmailId(userDto.getEmail());
        user.setPassword(getEncodedPassword(userDto.getPassword()));
        // extracting the role from DB and set it as a set
        user.setRole(roleRepository.findByRoleName(userDto.getRole().toUpperCase()));

        userCredentialsService.saveUser(user);

    }

    /**
     * This method receive the object from the queue of RabbitMq and then convert it into user credential and update the password to DB
     *
     * @param userDto
     */
    @RabbitListener(queues = QUEUE_TO_UPDATE_USER)
    public void getDataFromRabbitMqToUpdateUser(UserDto userDto) {
        UserCredentials user = new UserCredentials();
        user.setEmailId(userDto.getEmail());
        user.setPassword(getEncodedPassword(userDto.getPassword()));
        // extracting the role from DB and set it as a set
        user.setRole(roleRepository.findByRoleName(userDto.getRole().toUpperCase()));
        userCredentialsService.updateUser(user, userDto.getEmail());
    }

    /**
     * This method receive the emailId from the queue of RabbitMq and then delete the  user credentials details from the DB on the basis of same email id
     *
     * @param emailId
     */
    @RabbitListener(queues = QUEUE_TO_DELETE_USER)
    public void getDataFromRabbitMqToDeleteUser(String emailId) {
        userCredentialsService.deleteUser(emailId);
    }

    /**
     * this is a helper method that takes a string parameter and is used to encode the password using BCryptPasswordEncoder
     *
     * @param password
     * @return
     */
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

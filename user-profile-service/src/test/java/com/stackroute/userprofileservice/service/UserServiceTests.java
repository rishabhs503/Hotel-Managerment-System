package com.stackroute.userprofileservice.service;



import com.stackroute.userprofileservice.configuration.Producer;
import com.stackroute.userprofileservice.exception.UserFoundException;
import com.stackroute.userprofileservice.exception.UserNotFoundException;
import com.stackroute.userprofileservice.models.Address;
import com.stackroute.userprofileservice.models.Users;
import com.stackroute.userprofileservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {





    @Mock
    UserRepository userRepository;


    @Mock
    Producer producer;

    @InjectMocks
    UserServiceImpl userService;


    Users user1,user2;
    Address address1,address2;



    @BeforeEach
    public void setUp() {



        user1 = new Users(111 ,"Swastika","Shanker","9561245882",address1,"OWNER","swas.123@gmail.com","2022-11-24","1998-11-02","F","b5EA228N",true,"swas@123");
        address1 = new Address("Patna","Bihar","India","800014");

        user2 = new Users(112 ,"Swati","Sinha","9995421009",address2,"CUSTOMER","swati.999@gmail.com","2022-11-30","1994-10-25","F","a5EA228N",true,"root@123");
        address2 = new Address("Noida","Uttar Pradesh","India","110096");



    }

    @AfterEach
    public void tearDown() {
        user1= null;
        address1 = null;
    }


    @Test
    public void ToSaveUser_Success() throws UserFoundException {
        when(userRepository.findByEmailId(user1.getEmailId())).thenReturn(null);
        when(userRepository.save(user1)).thenReturn(user1);
        doNothing().when(producer).sendMessageToRabbitMqForEmail(any());

        assertEquals(user1, userService.saveUser(user1));

        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).findByEmailId(any());

    }


    @Test
    public void ToSaveUser_Failure() throws UserFoundException {
        when(userRepository.findByEmailId(user1.getEmailId())).thenReturn(user1);
        assertThrows(UserFoundException.class,()->userService.saveUser(user1));
        verify(userRepository, times(1)).findByEmailId(any());
    }


    @Test
    public void ToDeleteUser_Success() throws UserNotFoundException{

        when(userRepository.findById(user1.getUserId())).thenReturn(user1);
        boolean flag = userService.deleteUser(user1.getUserId());
        assertTrue(flag);
        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).deleteById(anyInt());


    }

    @Test
    public void ToDeleteUser_Failure()  throws UserNotFoundException {
        when(userRepository.findById(user1.getUserId())).thenReturn(null);
        assertThrows(UserNotFoundException.class,()->userService.deleteUser(user1.getUserId()));
        verify(userRepository, times(1)).findById(anyInt());

    }

    @Test
    public void ToUpdateUser_Success()  throws UserNotFoundException {
       when(userRepository.findById(user1.getUserId())).thenReturn(user1);
        doNothing().when(producer).sendMessageToRabbitMqToUpdateUser(any());
        assertEquals(user1, userService.updateUser(user1,user1.getUserId()));
        verify(userRepository, times(1)).findById(anyInt());

    }


    @Test
    public void ToUpdateUser_Failure()  throws UserNotFoundException {
        when(userRepository.findById(user1.getUserId())).thenReturn(null);
        assertThrows(UserNotFoundException.class,()->userService.updateUser(user1,user1.getUserId()));
        verify(userRepository, times(1)).findById(anyInt());

    }


    @Test
    public void ToGetUser_Success()throws UserNotFoundException{
        when(userRepository.findById(user1.getUserId())).thenReturn(user1);
        assertEquals(user1, userService.getUserById(user1.getUserId()));

    }

    @Test
    public void ToGetUser_Failure()throws UserNotFoundException{
        when(userRepository.findById(user1.getUserId())).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class,()->userService.getUserById(user1.getUserId()));

    }

    @Test
    public void ToGetAllUsers_Success()throws UserNotFoundException{

        List<Users> records  =new ArrayList<>(Arrays.asList(user1,user2));
        when(userRepository.findAll()).thenReturn((records));
        assertEquals(2, userService.getAllUsers().size());

    }

    @Test
    public void ToGetAllUsers_Failure()throws UserNotFoundException{
        when(userRepository.findAll()).thenReturn(null);
        when(userRepository.findAll()).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class,()->userService.getAllUsers());

    }

}




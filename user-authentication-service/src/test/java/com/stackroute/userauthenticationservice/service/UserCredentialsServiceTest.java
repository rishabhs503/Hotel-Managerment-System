package com.stackroute.userauthenticationservice.service;

import com.stackroute.userauthenticationservice.entity.Role;
import com.stackroute.userauthenticationservice.entity.UserCredentials;
import com.stackroute.userauthenticationservice.exception.UserFoundException;
import com.stackroute.userauthenticationservice.exception.UserNotFoundException;
import com.stackroute.userauthenticationservice.repository.UserCredentialsRepository;
import com.stackroute.userauthenticationservice.service.impl.UserCredentialsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCredentialsServiceTest {
    private UserCredentials user;

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @InjectMocks
    private UserCredentialsServiceImpl userCredentialsService;

    @BeforeEach
    public void setUp() {
        user = new UserCredentials();
        user.setEmailId("abc.12@gmail.com");
        user.setPassword("password#543!");
        user.setRole(new Role(1, "OWNER"));

    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

    @Test
    void saveUserTest() throws UserFoundException {
        when(userCredentialsRepository.findById(user.getEmailId())).thenReturn(Optional.empty());
        when(userCredentialsRepository.save(user)).thenReturn(user);

        assertEquals(user, userCredentialsService.saveUser(user));

        verify(userCredentialsRepository, times(1)).save(any());
        verify(userCredentialsRepository, times(1)).findById(any());
    }

    @Test
    void saveUserTestFailure() throws UserFoundException {
        when(userCredentialsRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        assertThrows(UserFoundException.class,()-> userCredentialsService.saveUser(user));
        verify(userCredentialsRepository, times(1)).findById(any());
    }

    @Test
    void updateUserTest() {
        when(userCredentialsRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        when(userCredentialsRepository.save(user)).thenReturn(user);

        assertEquals(user, userCredentialsService.updateUser(user, user.getEmailId()));

        verify(userCredentialsRepository, times(1)).save(any());
        verify(userCredentialsRepository, times(1)).findById(any());
    }

    @Test
    void updateUserTestFailure() {
        when(userCredentialsRepository.findById(user.getEmailId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->userCredentialsService.updateUser(user,user.getEmailId()));

        verify(userCredentialsRepository, times(1)).findById(any());
    }

    @Test
    void deleteUserTest() {
        when(userCredentialsRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        boolean flag = userCredentialsService.deleteUser(user.getEmailId());

        assertTrue(flag);

        verify(userCredentialsRepository, times(1)).findById(any());
        verify(userCredentialsRepository, times(1)).deleteById(any());
    }

    @Test
    void deleteUserTestFailure() {
        when(userCredentialsRepository.findById(user.getEmailId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->userCredentialsService.deleteUser(user.getEmailId()));

        verify(userCredentialsRepository, times(1)).findById(any());
    }
}

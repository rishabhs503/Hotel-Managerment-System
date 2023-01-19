package com.stackroute.userprofileservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userprofileservice.exception.UserFoundException;
import com.stackroute.userprofileservice.exception.UserNotFoundException;
import com.stackroute.userprofileservice.models.Address;
import com.stackroute.userprofileservice.models.Users;
import com.stackroute.userprofileservice.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {


    @Autowired
    MockMvc mockMvc;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;


    Users user1,user2;
    Address address1,address2;



    @BeforeEach
    public void setUp() {



        user1 = new Users(111 ,"Swastika","Shanker","9561245882",address1,"OWNER","swas.123@gmail.com","2022-11-24","1998-11-02","F","b5EA228N",true,"swas@123");
        address1 = new Address("Patna","Bihar","India","800014");

        user2 = new Users(112 ,"Swati","Sinha","9995421009",address2,"CUSTOMER","swati.999@gmail.com","2022-11-30","1994-10-25","F","a5EA228N",true,"root@123");
        address2 = new Address("Noida","Uttar Pradesh","India","110096");


        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();



    }

    @AfterEach
    public void tearDown() {
        user1= null;
        address1 = null;
    }

    @Test
    public void addUser_success()throws Exception{

        when(userService.saveUser(any())).thenReturn(user1);
        mockMvc.perform(post("/registration/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).saveUser(any());
    }

    @Test
    public void addUser_failure()throws Exception{

        when(userService.saveUser(any())).thenThrow(UserFoundException.class);
        mockMvc.perform(post("/registration/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).saveUser(any());
    }



    @Test
    public void deleteUser_success() throws Exception {

        when(userService.deleteUser(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/registration/delete/111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).deleteUser(anyInt());
    }

    @Test
    public void deleteUser_failure() throws Exception {

        when(userService.deleteUser(anyInt())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(delete("/registration/delete/111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).deleteUser(anyInt());
    }




    @Test
    public void getUser_success()throws Exception{
        List<Users> records  =new ArrayList<>(Arrays.asList(user1,user2));
        when(userService.getAllUsers()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/registration/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).getAllUsers();

    }


    @Test
    public void getUser_failure()throws Exception{
        when(userService.getAllUsers()).thenThrow(UserNotFoundException.class);


        mockMvc.perform(get("/registration/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                      .andDo(print());
        verify(userService, times(1)).getAllUsers();

    }


    @Test
    public void  getUserById_success()throws Exception{

        when(userService.getUserById(anyInt())).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/registration/users/111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).getUserById(user1.getUserId());
    }

    @Test
    public void  getUserById_failure()throws Exception{

        when(userService.getUserById(anyInt())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/registration/users/111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(userService, times(1)).getUserById(user1.getUserId());
    }



    @Test
    public void updateUser_success()throws Exception{
        when(userService.updateUser(any(),anyInt())).thenReturn(user1);
        mockMvc.perform(put("/registration/update/111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).updateUser(any(),anyInt());

    }

    @Test
    public void updateUser_failure()throws Exception{
        when(userService.updateUser(any(),anyInt())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(put("/registration/update/111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).updateUser(any(),anyInt());

    }





    private static String jsonToString(final Object o) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(o);
            result = jsonContent;
            return result;

        } catch (JsonProcessingException e) {
            result = "JsonProcessingException";
        }
        return result;
    }
}





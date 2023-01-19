package com.stackroute.userprofileservice.controller;


import com.stackroute.userprofileservice.exception.UserFoundException;
import com.stackroute.userprofileservice.exception.UserNotFoundException;
import com.stackroute.userprofileservice.models.Users;
import com.stackroute.userprofileservice.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@ApiOperation(value = "/registration", tags = "User Registration Controller")
@RestController
@RequestMapping("/registration")
public class UserController {

    @Autowired
    private UserService userService;


    private ResponseEntity responseEntity;

    /**
     * This endpoint allows to Add new User.
     * @author Swastika Shanker
     */
    @ApiOperation(value = "Register user and generate otp.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED ", response = String.class),
            @ApiResponse(code = 404, message = "NOT FOUND !!", response = String.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = String.class)

    })
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody @Valid Users user) throws UserFoundException {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>("Otp Sent successfully.Kindly verify to complete registration.", HttpStatus.CREATED);
        } catch (UserFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * This endpoint allows to fetch the records of Users from the Database.
     * @author Swastika Shanker
     */

    @ApiOperation(value = "Display all users.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = String.class),
            @ApiResponse(code = 404, message = "NOT FOUND ", response = String.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = String.class)
    })
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers() throws UserNotFoundException{


        try {
            List<Users> list = userService.getAllUsers();
            responseEntity= ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (UserNotFoundException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Error try after sometime", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }



    @ApiOperation(value = "Display record of user by Id.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = String.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = String.class),
            @ApiResponse(code = 404, message = "NOT FOUND ", response = String.class)
    })

    /**
     * This endpoint allows to fetch the record of a User by ID from the Database.
     * @author Swastika Shanker
     */

    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") int id)  throws UserNotFoundException {

        try {
           Users user= userService.getUserById(id);
           responseEntity= ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (UserNotFoundException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Error try after sometime", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;


    }



    @ApiOperation(value = "Delete record of user by Id.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = String.class),
            @ApiResponse(code = 404, message = "NOT FOUND ", response = String.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = String.class)
    })

    /**
     * This endpoint allows to delete the record of a User by ID from the Database.
     * @author Swastika Shanker
     */


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>  deleteUser(@PathVariable int id) throws UserNotFoundException {
        try {
           userService.deleteUser(id);
            responseEntity = new ResponseEntity<>("Successfully deleted!!!!", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Error try after sometime", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }




    @ApiOperation(value = "Update record of user by Id.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = String.class),
            @ApiResponse(code = 404, message = "NOT FOUND ", response = String.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = String.class)
    })
    /**
     * This endpoint allows to update the record of a User by ID from the Database.
     * @author Swastika Shanker
     */

    @PutMapping("/update/{id}")
    public ResponseEntity<?>  updateUser(@RequestBody Users user, @PathVariable("id") int id) throws UserNotFoundException {
        try {
            userService.updateUser(user, id);
            responseEntity = new ResponseEntity<>("Successfully updated!!!!", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Error try after sometime", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }




    /**
     * This method allows to handle exceptions.
     * @author Swastika Shanker
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

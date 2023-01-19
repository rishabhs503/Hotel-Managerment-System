package com.stackroute.userprofileservice.payload;


import lombok.*;

@Getter
@Setter
@ToString
public class UserDto {
    private String email;
    private String password;
    private String role;

}

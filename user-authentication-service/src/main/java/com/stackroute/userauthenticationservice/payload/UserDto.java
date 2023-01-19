package com.stackroute.userauthenticationservice.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Generated
public class UserDto {
    private String email;
    private String password;
    private String role;

}
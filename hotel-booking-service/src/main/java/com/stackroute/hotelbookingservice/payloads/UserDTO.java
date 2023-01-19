package com.stackroute.hotelbookingservice.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String emailId;
}
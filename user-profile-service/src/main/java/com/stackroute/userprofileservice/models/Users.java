package com.stackroute.userprofileservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Users {

    @Id
    private int userId;

    @NotNull
    private String firstName;

    private String lastName;

    @NotNull
    @Pattern(regexp = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$", message = "Enter a valid mobile number.")
    private String mobileNumber;


    private Address address;

    private String role;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String emailId;


    private String createdAt;
    private String dob;
    private String gender;

    private String otp;
    private boolean isVerified;


    private String password;
}




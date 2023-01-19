package com.stackroute.smsservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @NotNull
    private String numbers;

    @NotNull
    private String role;

    @NotNull
    private String firstname;

    private String lastname;

    @NotNull
    private String requestType;

    private MessageDetails details;
}

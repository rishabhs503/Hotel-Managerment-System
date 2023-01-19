package com.stackroute.userauthenticationservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserCredentials {

    @Id
    private String emailId;

    private String password;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

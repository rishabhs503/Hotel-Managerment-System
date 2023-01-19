package com.stackroute.userauthenticationservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    private Integer roleId;

    private String roleName;


}
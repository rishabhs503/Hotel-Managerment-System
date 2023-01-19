package com.stackroute.userauthenticationservice.service;

import com.stackroute.userauthenticationservice.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> initRoles(List<Role> roles);
}

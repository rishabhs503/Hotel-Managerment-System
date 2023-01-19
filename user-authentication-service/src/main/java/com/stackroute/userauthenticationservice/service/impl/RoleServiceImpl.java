package com.stackroute.userauthenticationservice.service.impl;

import com.stackroute.userauthenticationservice.entity.Role;
import com.stackroute.userauthenticationservice.repository.RoleRepository;
import com.stackroute.userauthenticationservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class has repository autowired to it and calls the downstream methods from JpaRepository to perform CRUD operations
 */
@Service
public class RoleServiceImpl implements RoleService {
    /**
     * creating the instance of repository using autowired annotation
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * This method creates 2 roles and add them to the database
     */
    @Override
    public List<Role> initRoles(List<Role> roles) {
        // adding the data to DB
        return roleRepository.saveAll(roles);
    }
}

package com.stackroute.userauthenticationservice.repository;

import com.stackroute.userauthenticationservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * This interface will provide all the pre-defined methods to interact with the database for Role entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}


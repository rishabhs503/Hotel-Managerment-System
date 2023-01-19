package com.stackroute.userauthenticationservice.repository;

import com.stackroute.userauthenticationservice.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface will provide all the pre-defined methods to interact with the database for UserCredentials entity
 */
@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, String> {
}
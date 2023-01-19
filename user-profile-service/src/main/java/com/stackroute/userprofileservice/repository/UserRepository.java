package com.stackroute.userprofileservice.repository;


import com.stackroute.userprofileservice.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface UserRepository extends MongoRepository<Users,Integer> {



    Users findById(int id);

    Users findByEmailId(String emailId);

    Users findByMobileNumber(String mobileNumber);




}

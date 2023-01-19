package com.stackroute.hotelpropertyservice.repository;

import com.stackroute.hotelpropertyservice.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    @Query("{ 'city': {$regex: ?0, $options: 'i'}, 'state': {$regex: ?1 , $options: 'i'}}")
    List<Address> findByCityAndState(String city, String state);

//    @Query("{ 'city': {$regex : ?0, $options: 'i'}}")
//    List<Address> findByCity(String city);
//
//    @Query("{$and: [{'$or' : [{ 'city': {$regex : ?0, $options: 'i'}}, {'state': {$regex : ?1, $options: 'i'}}]}, { 'country' : {$regex : ?2, $options: 'i'} }]}")
//    List<Address> findAddressByCityAndStateAndCountry(String city, String state, String country);

}

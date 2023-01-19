package com.stackroute.hotelpropertyservice.repository;

import com.stackroute.hotelpropertyservice.model.Hotel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, Integer> {

    @Query("{'address.$id': ?0 }")
    List<Hotel> findByAddressId(ObjectId addressId);

    @Query("{'name': ?0 }")
    List<Hotel> findByName(String hotelName);

}

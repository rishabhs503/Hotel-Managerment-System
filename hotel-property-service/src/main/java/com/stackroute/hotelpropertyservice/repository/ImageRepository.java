package com.stackroute.hotelpropertyservice.repository;

import com.stackroute.hotelpropertyservice.model.Images;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository<Images, String> {

    @Query("{hotelId :?0}")
    Images getImagesByHotelId(Integer hotelId);
}

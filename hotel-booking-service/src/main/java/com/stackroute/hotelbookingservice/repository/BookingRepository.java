package com.stackroute.hotelbookingservice.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.stackroute.hotelbookingservice.entities.Booking;


public interface BookingRepository extends MongoRepository<Booking,Integer>{
    Booking findById(int bookingId);

}

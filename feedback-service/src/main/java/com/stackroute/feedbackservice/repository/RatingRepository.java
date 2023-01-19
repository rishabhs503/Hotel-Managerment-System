package com.stackroute.feedbackservice.repository;

import com.stackroute.feedbackservice.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Feedback, Integer> {
//    @Query("{feedbackId :?0}")
//    Feedback findByFeedbackId(Feedback feedbackId);

//    @Query("{hotelId :?0}")
//    Feedback findByHotelId(Integer hotelId);

    @Query(value= "{hotelId: ?0}", fields="{Ratings:0}")
    List<Feedback> findByHotelId(Integer hotelId);
}

package com.stackroute.feedbackservice.repository;

import com.stackroute.feedbackservice.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, Long> {

    @Query("{hotelId :?0}")
    List<Feedback> getFeedbacksByHotelId(Integer hotelId);

    @Query("{userId :?0, hotelId: ?1}")
    Feedback findByUserId(Integer userId, Integer hotelId);

    @Query("{createdAt :?0, userId: ?1, hotelId :?2}")
    List<Feedback> findByCreatedAt(Date createdAt, String userId, String hotelId);

}
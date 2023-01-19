package com.stackroute.feedbackservice.service;

import com.stackroute.feedbackservice.exception.FeedbackAlreadyExisted;
import com.stackroute.feedbackservice.exception.FoundInvalidData;
import com.stackroute.feedbackservice.exception.NoSuchFeedbackPresent;
import com.stackroute.feedbackservice.model.Feedback;
import com.stackroute.feedbackservice.model.Ratings;
import com.stackroute.feedbackservice.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author vipin
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{

    private final  FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<?> saveFeedback(Integer hotelId, Integer userId, Feedback feedback) throws FeedbackAlreadyExisted, FoundInvalidData {

        if (userId != null && userId != 0 && hotelId != null && hotelId != 0) {
            Feedback exitsFeedback = feedbackRepository.findByUserId(userId, hotelId);

            if (exitsFeedback != null) {
                throw new FeedbackAlreadyExisted();
            }

            Ratings currentRatings = feedback.getRatings();
            Double totalRating = currentRatings.getCleanliness() + currentRatings.getFacilities() + currentRatings.getHospitality() + currentRatings.getValueformoney() + currentRatings.getLocation();

            feedback.setAverageRating(totalRating/5);

            feedback.setCreatedAt(new Date());
            feedback.setUpdatedAt(new Date());

            feedback.setUserId(userId);
            feedback.setHotelId(hotelId);

            Feedback savedFeedback = feedbackRepository.save(feedback);

            return new ResponseEntity<>(savedFeedback, HttpStatus.OK);
        }

        throw new FoundInvalidData();
    }

    @Override
    public Feedback updateFeedback(Long feedbackId, Feedback feedback) throws NoSuchFeedbackPresent {

        Feedback isFeedbackpresent = feedbackRepository.findById(feedbackId).orElse(null);

        if (isFeedbackpresent == null) {
             throw new NoSuchFeedbackPresent();
        } else {
            if (feedback.getMessage() != null) {
                isFeedbackpresent.setMessage(feedback.getMessage());
            }

            if (feedback.getTitle() != null) {
                isFeedbackpresent.setTitle(feedback.getTitle());
            }

            if (feedback.getFilters() != null) {
                isFeedbackpresent.setFilters(feedback.getFilters());
            }

            isFeedbackpresent.setUpdatedAt(new Date());

            return feedbackRepository.save(isFeedbackpresent);
        }
    }


    @Override
    public boolean deleteFeedback(Long feedbackId) {
        boolean isFeedbackPresent = feedbackRepository.findById(feedbackId).isPresent();

        if (isFeedbackPresent) {
            feedbackRepository.deleteById(feedbackId);
            return true;
        }

        return false;
    }

    @Override
    public HashMap<String,?> getFeedbacksByHotelId(Integer hotelId) {
        HashMap<String, Object> feedbackWithRatings = new HashMap<>();

        List<Feedback> feedbacksByHotelId = feedbackRepository.getFeedbacksByHotelId(hotelId);

        if (feedbacksByHotelId.isEmpty()) {
            return null;
        }

        Double hotelAverageRating = calculateHotelAverageRating(feedbacksByHotelId);
        feedbackWithRatings.put("Feedbacks", feedbacksByHotelId);
        feedbackWithRatings.put("HotelAverageRating", hotelAverageRating);
        return feedbackWithRatings;
    }

    @Override
    public ResponseEntity<?> saveFeedbackReply(Integer userId, Long feedbackId, String reply) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElse(null);

        if (feedback == null) {
            return new ResponseEntity<>("No feedback present", HttpStatus.BAD_REQUEST);
        }

        feedback.setReply(reply);
        feedbackRepository.save(feedback);

        return new ResponseEntity<>("successfully added reply", HttpStatus.OK);
    }

    private Double calculateHotelAverageRating(List<Feedback> feedbacksByHotelId) {

        Double hotelAverageRating, totalFeedbackAverageRating = 0.0;

        for (Feedback feedback : feedbacksByHotelId) {
            totalFeedbackAverageRating += feedback.getAverageRating();
        }

        hotelAverageRating = totalFeedbackAverageRating/feedbacksByHotelId.size();

        return hotelAverageRating;
    }

}

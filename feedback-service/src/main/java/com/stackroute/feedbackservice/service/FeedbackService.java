package com.stackroute.feedbackservice.service;

import com.stackroute.feedbackservice.exception.FeedbackAlreadyExisted;
import com.stackroute.feedbackservice.exception.FoundInvalidData;
import com.stackroute.feedbackservice.exception.NoSuchFeedbackPresent;
import com.stackroute.feedbackservice.model.Feedback;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface FeedbackService {

    ResponseEntity<?> saveFeedback(Integer hotelId, Integer userId, Feedback feedback) throws FeedbackAlreadyExisted, FoundInvalidData;
    Feedback updateFeedback(Long feedbackId, Feedback feedback) throws NoSuchFeedbackPresent;
    boolean deleteFeedback(Long feedbackId);
    HashMap<String,?> getFeedbacksByHotelId(Integer hotelId);
    ResponseEntity<?> saveFeedbackReply(Integer userId, Long feedbackId, String reply);
}

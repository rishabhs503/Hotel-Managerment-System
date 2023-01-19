package com.stackroute.feedbackservice.controller;

import com.netflix.discovery.converters.Auto;
import com.stackroute.feedbackservice.exception.FeedbackAlreadyExisted;
import com.stackroute.feedbackservice.exception.FoundInvalidData;
import com.stackroute.feedbackservice.exception.NoFeedbackFoundWithThisHotelId;
import com.stackroute.feedbackservice.exception.NoSuchFeedbackPresent;
import com.stackroute.feedbackservice.model.Feedback;
import com.stackroute.feedbackservice.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * @param feedback
     * createFeedBack controller is for creating new feedback & will return success or bad request response
     **/
    @PostMapping(value = "/feedback/create")
    public ResponseEntity<?> createFeedBack(@RequestParam("hotelId") Integer hotelId, @RequestParam("userId") Integer userId, @RequestBody Feedback feedback) throws FeedbackAlreadyExisted, FoundInvalidData {
        return feedbackService.saveFeedback(hotelId, userId, feedback);
    }

    /**
     * @param feedback
     * @param feedbackId
     * updateFeedBack controller is for creating update feedback & will return success or bad request response
     **/
    @PutMapping("/feedback/update/{feedbackId}")
    public Feedback updateFeedBack(@PathVariable Long feedbackId, @RequestBody Feedback feedback) throws NoSuchFeedbackPresent {
        return feedbackService.updateFeedback(feedbackId, feedback);
    }

    /**
     * @param feedbackId
     * deleteFeedback controller is for deleting the feedback
     **/
    @DeleteMapping("/feedback/delete/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long feedbackId) throws NoSuchFeedbackPresent {
        boolean status = feedbackService.deleteFeedback(feedbackId);
        if (status) {
             return new ResponseEntity<>("Successfully feedback deleted!!!",HttpStatus.ACCEPTED);
        }
        throw new NoSuchFeedbackPresent();
    }

    /**
     * @param hotelId
     * fetchFeedbacksByHotelId controller return the list of all feedbacks based on hotel
     **/
    @GetMapping("/fetchByHotelId/{hotelId}")
    public HashMap<String,?> fetchFeedbacksByHotelId(@PathVariable Integer hotelId) throws NoFeedbackFoundWithThisHotelId {
        HashMap<String, ?> feedbacksByHotelId = feedbackService.getFeedbacksByHotelId(hotelId);
        if (feedbacksByHotelId.isEmpty()) {
            throw new NoFeedbackFoundWithThisHotelId();
        }
        return feedbacksByHotelId;
    }

    @PutMapping("/addedReplyByAdmin/{userId}/{feedbackId}")
    public ResponseEntity<?> addedFeedbackReply(@PathVariable Integer userId, @PathVariable("feedbackId") Long feedbackId, @RequestParam("reply") String reply) throws NoSuchFeedbackPresent {
        if (userId != null && userId != 0 && feedbackId != null && feedbackId != 0 && !reply.isEmpty()) {
            return feedbackService.saveFeedbackReply(userId, feedbackId, reply);
        }
        return new ResponseEntity<>("Found invalid data", HttpStatus.BAD_REQUEST);
    }
}

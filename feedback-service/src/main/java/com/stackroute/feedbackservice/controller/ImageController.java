package com.stackroute.feedbackservice.controller;

import com.stackroute.feedbackservice.exception.NoSuchFeedbackPresent;
import com.stackroute.feedbackservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/feedback/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @PostMapping(value = "/save/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveFeedbackImages(@RequestParam(value = "feedbackId") Long id, @RequestPart(value = "images") MultipartFile[] images) throws NoSuchFeedbackPresent {
        return  imageService.saveImages(id, images);
    }

}

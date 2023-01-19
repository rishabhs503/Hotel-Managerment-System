package com.stackroute.feedbackservice.service;

import com.stackroute.feedbackservice.exception.NoSuchFeedbackPresent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ResponseEntity<?> saveImages(Long id, MultipartFile[] images);
}

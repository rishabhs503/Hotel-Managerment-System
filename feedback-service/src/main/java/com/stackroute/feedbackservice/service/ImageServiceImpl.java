package com.stackroute.feedbackservice.service;

import com.stackroute.feedbackservice.exception.NoSuchFeedbackPresent;
import com.stackroute.feedbackservice.model.Feedback;
import com.stackroute.feedbackservice.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageServiceImpl implements ImageService {

    private final FeedbackRepository feedbackRepository;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/feedback-service/uploads";

    @Autowired
    public ImageServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<?> saveImages(Long id, MultipartFile[] images) {

        Feedback feedback = feedbackRepository.findById(id).orElse(null);

        if (feedback == null) {
            return new ResponseEntity<>("No feedback existed with this id : "+ id, HttpStatus.BAD_REQUEST);
        }

        List<String> urls = new ArrayList<>();

        // extract images => image & upload it
        Arrays.asList(images)
                .stream()
                .forEach(image -> urls.add(uploadImages(id, image)));


        feedback.setUrls(urls);
        feedbackRepository.save(feedback);

        return new ResponseEntity<>("Images added successfully : "+ id, HttpStatus.OK);
    }

    /**
     * This uploadFiles upload an image on the given location
     * @param id
     * @param file
     * @return
     */
    private String uploadImages(Long id, MultipartFile file)
    {
        try {
            Path copyLocation = Paths.get(UPLOAD_DIRECTORY+"/"+ id +"/"+ File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.createDirectories(copyLocation);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            return copyLocation.toString();
        } catch (IOException e) {
            throw new RuntimeException("Inavlid files");
        }
    }


}

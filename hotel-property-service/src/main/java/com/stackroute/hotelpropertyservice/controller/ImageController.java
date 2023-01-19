package com.stackroute.hotelpropertyservice.controller;

import com.stackroute.hotelpropertyservice.model.Images;
import com.stackroute.hotelpropertyservice.service.HotelService;
import com.stackroute.hotelpropertyservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Hotel Image controller
 * @author vinutha
 */
@RestController
@RequestMapping("/hotel/images")
public class ImageController {

    private final ImageService hotelImageService;

    private final HotelService hotelService;

    @Autowired
    public ImageController(ImageService imageService, HotelService hotelService) {
        this.hotelImageService = imageService;
        this.hotelService = hotelService;
    }

    /**
     *
     * @param hotelId
     * @param images
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateHotelImages(@RequestPart(value = "HotelId") Integer hotelId, @RequestPart(value = "images") MultipartFile[] images){
        hotelImageService.updateImages(hotelId, images);
        return new ResponseEntity<String>("Successfully, Updated hotel Images", HttpStatus.OK);
    }

    /**
     *
     * @param hotelId
     * @return
     */
    @GetMapping("/fetchById/{hotelId}")
    public Images fetchImagesByHotelId(@PathVariable("hotelId") Integer hotelId){
        Images paths = hotelImageService.getImagesByHotelId(hotelId);
        return paths;
    }

    @PostMapping(value = "/save/pictures/{hotelId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveImagesByHotelId(@RequestPart(value = "images") MultipartFile[] images, @RequestParam("hotelId") Integer hotelId){
        Images paths = hotelImageService.saveImages(hotelId, images);
        boolean status = hotelService.saveImagesPath(hotelId, paths);

        if (status) {
            return new ResponseEntity<String>("Successfully, Added hotel Images", HttpStatus.OK);
        }

        return new ResponseEntity<String>("something went wrong", HttpStatus.CONFLICT);
    }


}

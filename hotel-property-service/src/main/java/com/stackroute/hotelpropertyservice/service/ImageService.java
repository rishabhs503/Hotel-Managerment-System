package com.stackroute.hotelpropertyservice.service;

import com.stackroute.hotelpropertyservice.model.Images;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Images saveImages(Integer hotelId, MultipartFile[] images);
    Images getImagesByHotelId(Integer hotelId);
    String updateImages(Integer hotelId, MultipartFile[] images);
}

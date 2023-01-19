package com.stackroute.hotelpropertyservice.service;

import com.stackroute.hotelpropertyservice.exception.HotelAlreadyExistException;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisAddress;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisId;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisName;
import com.stackroute.hotelpropertyservice.model.Hotel;
import com.stackroute.hotelpropertyservice.model.Images;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface HotelService {
	ResponseEntity<String> registerHotel(Hotel hotelData, String addressId, int userId)throws HotelAlreadyExistException;

    ResponseEntity<String> updateHotelDetails(Hotel hotel, Integer hotelId) throws NoHotelFoundWithThisId;

    List<Hotel> getHotelsByLocation(String city) throws NoHotelFoundWithThisAddress;

    List<Hotel> getHotelsByName(String hotelName) throws NoHotelFoundWithThisName;

    Optional<Hotel> getHotelById(Integer hotelId) throws NoHotelFoundWithThisId;

    HashMap<String,?> getHotelAndFeedbackByHotelId(Integer hotelId) throws NoHotelFoundWithThisId;

    boolean saveImagesPath(Integer hotelId, Images paths);

    boolean deleteHotelById(Integer hotelId) throws NoHotelFoundWithThisId;
}

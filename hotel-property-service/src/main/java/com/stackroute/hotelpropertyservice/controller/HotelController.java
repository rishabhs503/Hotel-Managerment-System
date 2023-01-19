package com.stackroute.hotelpropertyservice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.stackroute.hotelpropertyservice.exception.*;
import com.stackroute.hotelpropertyservice.model.Hotel;
import com.stackroute.hotelpropertyservice.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Hotel Controller
 *
 * @author vinutha
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * @param hotelDetails
     * @param addressId
     * @return
     * @throws IOException
     */

    @PostMapping(value = "/register/{address}/{userId}")
    public ResponseEntity<String> registerHotel(@RequestBody Hotel hotelDetails, @PathVariable("address") String addressId, @PathVariable("userId") int userId) throws IOException {
        try {
            if (addressId == null || addressId.isEmpty() && userId != 0 ) {
                throw new RequiredParameterMustBeRequired();
            }

            return hotelService.registerHotel(hotelDetails, addressId, userId);
        } catch (HotelAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (RequiredParameterMustBeRequired e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update hotel details controller
     *
     * @param hotelPropertyData
     * @param hotelId
     * @return
     * @throws IOException
     */

    @PutMapping(value = "/updateByHotelId/{hotelId}")
    public ResponseEntity<?> updateHotelDetails(@RequestBody Hotel hotelPropertyData, @PathVariable("hotelId") Integer hotelId) throws IOException {
        try {
            if (hotelId == null || hotelId == 0) {
                throw new RequiredParameterMustBeRequired();
            }

            return hotelService.updateHotelDetails(hotelPropertyData, hotelId);
        } catch (NoHotelFoundWithThisId noHotelFoundWithThisId) {
            return new ResponseEntity<>(noHotelFoundWithThisId.getMessage(), HttpStatus.CONFLICT);
        } catch (RequiredParameterMustBeRequired e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * search hotel by address
     *
     * @param address
     * @return
     * @throws IOException
     */

    @GetMapping(value = "/searchByLocation/{address}")
    public List<Hotel> searchHotelsByLocation(@PathVariable("address") String address) throws NoHotelFoundWithThisAddress {
        return hotelService.getHotelsByLocation(address);
    }

    /**
     * Search hotel by hotel name
     *
     * @param name
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/searchByName/{name}")
    public List<Hotel> searchHotelsByName(@PathVariable("name") String name) throws NoHotelFoundWithThisName, RequiredParameterMustBeRequired {
        if (name.isEmpty() || name == null) {
            throw new RequiredParameterMustBeRequired();
        }
        return hotelService.getHotelsByName(name);
    }

    /**
     * Search hotel by hotel Id
     *
     * @param hotelId
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/searchByHotelId/{hotelId}")
    public Hotel searchHotelById(@PathVariable("hotelId") Integer hotelId) throws NoHotelFoundWithThisId {

        return hotelService.getHotelById(hotelId).get();
    }

    /**
     * Search hotel by hotel Id
     *
     * @param hotelId
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = "/delete/{hotelId}")
    public ResponseEntity<String> deleteHotelById(@PathVariable("hotelId") Integer hotelId) throws NoHotelFoundWithThisId, RequiredParameterMustBeRequired {
        if (hotelId == null || hotelId == 0) {
            throw new RequiredParameterMustBeRequired();
        }
        boolean status = hotelService.deleteHotelById(hotelId);
        if (status) {
            return new ResponseEntity<>("Successfully removed hotel", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong, Please connect with Easemystay support. ", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    /**
     * @apiNote Fetch hotel and feedbacks with rating
     * @param hotelId
     * @return
     * @throws NoHotelFoundWithThisId
     */
    @GetMapping(value = "/fetchHotelAndFeedbacks/{hotelId}")
    public HashMap<String,?> hotelAndFeedbacksByHotelId(@PathVariable("hotelId") Integer hotelId) throws NoHotelFoundWithThisId, RequiredParameterMustBeRequired {
        if (hotelId == null || hotelId == 0) {
            throw new RequiredParameterMustBeRequired();
        }
        return hotelService.getHotelAndFeedbackByHotelId(hotelId);
    }
}
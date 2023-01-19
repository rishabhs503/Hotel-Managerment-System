package com.stackroute.hotelpropertyservice.controller;

import com.stackroute.hotelpropertyservice.model.Address;
import com.stackroute.hotelpropertyservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Find address based on provided string
     * @return
     */

    @GetMapping("/search")
    public List<Address> searchLocation(@RequestParam(required = false, defaultValue = "") String city, @RequestParam(required = false, defaultValue = "") String state, @RequestParam(required = false, defaultValue = "india") String country) {
        return addressService.getAddress(city, state, country);
    }


    /**
     * This is for importing india cities with state in Address collection
     * @throws IOException
     */

    @PostConstruct
    public void dummyDataUpload() throws IOException {
        addressService.saveDummyAddressData();
    }
}

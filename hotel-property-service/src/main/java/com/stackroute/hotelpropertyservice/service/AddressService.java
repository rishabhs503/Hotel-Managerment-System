package com.stackroute.hotelpropertyservice.service;

import com.stackroute.hotelpropertyservice.model.Address;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<Address> getAddress(String city, String state, String country);

    void saveDummyAddressData() throws IOException;

    Optional<Address> getAddressById(String id);
}

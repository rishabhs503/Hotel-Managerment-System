package com.stackroute.hotelpropertyservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.hotelpropertyservice.model.Address;
import com.stackroute.hotelpropertyservice.model.CitiesStateWithCountry;
import com.stackroute.hotelpropertyservice.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Service
public class AddressServiceImpl implements AddressService {

    private final String ADDRESS_JSON = "/Data/in.json";

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAddress(String city, String state, String country) {
         List<Address> address = addressRepository.findByCityAndState(city, state);
        return address;
    }

    @Override
    public Optional<Address> getAddressById(String id) {
        return addressRepository.findById(id);
    }


    @Override
    public void saveDummyAddressData() throws IOException {
        try {
            TypeReference< List<CitiesStateWithCountry>> typeReference = new TypeReference<List<CitiesStateWithCountry>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream(ADDRESS_JSON);
            List<CitiesStateWithCountry> addresses = new ObjectMapper().readValue(inputStream, typeReference);
            if (addresses != null && !addresses.isEmpty()) {
                addresses.forEach(address -> addressRepository.save(new Address( address.getPopulation_proper(), address.getCity(), address.getAdmin_name(), address.getCountry(), "0010101")));
            }
        } catch (Exception ex) {
            System.out.println("Unable to save addresses " + ex.getMessage());
        }
    }


}

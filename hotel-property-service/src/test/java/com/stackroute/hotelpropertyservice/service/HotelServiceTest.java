package com.stackroute.hotelpropertyservice.service;

import com.stackroute.hotelpropertyservice.Configuration.Producer;
import com.stackroute.hotelpropertyservice.exception.HotelAlreadyExistException;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisAddress;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisId;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisName;
import com.stackroute.hotelpropertyservice.model.Address;
import com.stackroute.hotelpropertyservice.model.Hotel;
import com.stackroute.hotelpropertyservice.payload.UserDto;
import com.stackroute.hotelpropertyservice.repository.HotelRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {
    private Hotel hotel;
    private List<Hotel> hotels;
    private Address address;
    private UserDto user;
    private String addressId;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private AddressServiceImpl addressService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Producer producer;

    @BeforeEach
    public void setUp() {
        user = new UserDto();
        user.setUserId(1);
        user.setFirstName("John");
        user.setEmailId("abc@gmail.com");
        user.setMobileNumber("9875674567");

        addressId = "639477ee8751f05c8f829152";

        address = new Address();
        address.setAddressId(addressId);
        address.setCity("Haldwani");
        address.setState("Uttarakhand");
        address.setCountry("India");
        address.setZipCode("263139");

        hotel = new Hotel();
        hotel.setId(1001);
        hotel.setName("Taj Hotel");
        hotel.setAminities(new ArrayList<>());
        hotel.setUser(user);
        hotel.setAddress(address);

        hotels = new ArrayList<>();
        hotels.add(hotel);
    }

    @AfterEach
    public void tearDown() {
        hotel = null;
        addressId = "";
        address = null;
        user = null;
    }

    @Test
    void testHotelAddSuccess() throws HotelAlreadyExistException {

        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.empty());
        when(addressService.getAddressById(addressId)).thenReturn(Optional.of(address));
        when(restTemplate.getForObject("http://user-profile-service/registration/users/" + user.getUserId(), UserDto.class)).thenReturn(user);
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        assertEquals(new ResponseEntity<>("Successfully register : Hotel Id = " + hotel.getId(), HttpStatus.OK), hotelService.registerHotel(hotel, addressId, user.getUserId()));

        verify(hotelRepository, times(1)).findById(anyInt());
        verify(hotelRepository, times(1)).save(any());
    }

    @Test
    void testHotelAddFailure() {
        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.ofNullable(hotel));

        assertThrows(HotelAlreadyExistException.class, () -> hotelService.registerHotel(hotel, addressId, user.getUserId()));

        verify(hotelRepository, times(1)).findById(anyInt());
    }

    @Test
    void testHotelUpdateSuccess() throws NoHotelFoundWithThisId {
        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.ofNullable(hotel));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        assertEquals(new ResponseEntity<>("Successfully data updated!!!", HttpStatus.OK), hotelService.updateHotelDetails(hotel, hotel.getId()));

        verify(hotelRepository, times(1)).findById(anyInt());
        verify(hotelRepository, times(1)).save(any());
    }

    @Test
    void testHotelUpdateFailure() {
        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.empty());

        assertThrows(NoHotelFoundWithThisId.class, () -> hotelService.updateHotelDetails(hotel, hotel.getId()));

        verify(hotelRepository,times(1)).findById(anyInt());

    }

    @Test
    void testHotelDeleteSuccess() throws NoHotelFoundWithThisId {
        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.ofNullable(hotel));
        boolean flag = hotelService.deleteHotelById(hotel.getId());

        assertTrue(flag);

        verify(hotelRepository,times(1)).findById(anyInt());
        verify(hotelRepository,times(1)).deleteById(anyInt());

    }

    @Test
    void testHotelDeleteFailure(){
        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.empty());

        assertThrows(NoHotelFoundWithThisId.class,()->hotelService.deleteHotelById(hotel.getId()));

        verify(hotelRepository,times(1)).findById(anyInt());
    }

    @Test
    void testGetHotelByLocation() throws NoHotelFoundWithThisAddress {
        when(hotelRepository.findByAddressId(new ObjectId(addressId))).thenReturn(hotels);

        assertEquals(1,hotelService.getHotelsByLocation(addressId).size());

        verify(hotelRepository,times(1)).findByAddressId(any());
    }

    @Test
    void testGetHotelByName() throws NoHotelFoundWithThisName {
        when(hotelRepository.findByName(hotel.getName())).thenReturn(hotels);

        assertEquals(1,hotelService.getHotelsByName(hotel.getName()).size());

        verify(hotelRepository,times(1)).findByName(any());
    }

    @Test
    void testGetHotelById() throws NoHotelFoundWithThisId {
        when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.ofNullable(hotel));

        assertEquals(Optional.ofNullable(hotel), hotelService.getHotelById(hotel.getId()));

        verify(hotelRepository,times(2)).findById(anyInt());
    }

}

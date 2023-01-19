package com.stackroute.hotelbookingservice.service;

import com.stackroute.hotelbookingservice.configuration.Producer;
import com.stackroute.hotelbookingservice.entities.Booking;
import com.stackroute.hotelbookingservice.payloads.*;
import com.stackroute.hotelbookingservice.repository.BookingRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    Producer producer;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    BookingServiceImpl bookingServiceImpl;

    Booking booking;
    UserDTO user;
    HotelDTO hotel;
    HotelAddressDto address;
    HotelContactDto contacts;
    List<RoomDto> rooms;

    @BeforeEach
    public void setUp() {
        booking = new Booking();
        user = new UserDTO();
        hotel = new HotelDTO();
        address = new HotelAddressDto();
        contacts = new HotelContactDto();
        rooms = new ArrayList<>();
        hotel.setContacts(contacts);
        hotel.setAddress(address);
        hotel.setRooms(rooms);
    }

    @AfterEach
    public void tearDown() {
        booking = null;
        user = null;
        hotel = null;
        address = null;
        contacts = null;
        rooms = null;
    }

    @Test
    public void saveBooking() {
        when(bookingRepository.save(booking)).thenReturn(booking);
        when(restTemplate.getForObject("http://user-profile-service/registration/users/" + 210, UserDTO.class)).thenReturn(user);
        when(restTemplate.getForObject("http://hotel-property-service/hotel/searchByHotelId/" + 2222, HotelDTO.class)).thenReturn(hotel);

        assertEquals(booking, bookingServiceImpl.saveBooking(booking, 210, 2222));

        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    public void deleteBooking() {

        when(bookingRepository.findById(booking.getBookingId())).thenReturn(booking);
        boolean flag = bookingServiceImpl.deleteBooking(booking.getBookingId());

        assertTrue(flag);

        verify(bookingRepository, times(1)).findById(anyInt());
        verify(bookingRepository, times(1)).delete(any());

    }

    @Test
    public void getBookingById() {
        when(bookingRepository.findById(booking.getBookingId())).thenReturn(booking);

        assertEquals(booking, bookingServiceImpl.getBookingByID(booking.getBookingId()));

        verify(bookingRepository, times(1)).findById(anyInt());
    }

    @Test
    public void getAllBookings() {
        List<Booking> records = new ArrayList<>(Arrays.asList(booking, booking));

        when(bookingRepository.findAll()).thenReturn((records));

        assertEquals(2, bookingServiceImpl.getBookings().size());

        verify(bookingRepository,times(1)).findAll();
    }

    @Test
    public void ToUpdateUser_Success() {
        when(bookingRepository.findById(booking.getBookingId())).thenReturn(booking);
        when(bookingRepository.save(booking)).thenReturn(booking);

        assertEquals(booking, bookingServiceImpl.updateBooking(booking, booking.getBookingId()));

        verify(bookingRepository, times(1)).findById(anyInt());
        verify(bookingRepository, times(1)).findById(anyInt());
    }

}

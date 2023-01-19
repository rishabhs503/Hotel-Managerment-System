package com.stackroute.hotelpropertyservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.hotelpropertyservice.exception.HotelAlreadyExistException;
import com.stackroute.hotelpropertyservice.model.Hotel;
import com.stackroute.hotelpropertyservice.service.HotelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelController hotelController;

    private Hotel hotel;
    private String addressId;
    private int userId;
    private List<Hotel> hotels;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        hotel = new Hotel();
        hotel.setId(1001);
        hotel.setName("Taj Hotel");
        hotel.setAminities(new ArrayList<>());
        hotels = new ArrayList<>();
        hotels.add(hotel);
        addressId = "639477ee8751f05c8f829152";
        userId = 101;
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
    }

    @AfterEach
    public void tearDown() {
        hotel = null;
        hotels = null;
        addressId = "";
        userId = 0;
    }

    @Test
    void testRegisterHotel() throws Exception {
        when(hotelService.registerHotel(any(), any(), anyInt()))
                .thenReturn(new ResponseEntity<>("Successfully register : Hotel Id = " + hotel.getId(), HttpStatus.OK));

        mockMvc.perform(post("/hotel/register/" + addressId + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(hotel)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(hotelService,times(1)).registerHotel(any(),any(),anyInt());
    }

    @Test
    void testUpdateHotel() throws Exception {
        when(hotelService.updateHotelDetails(any(), anyInt()))
                .thenReturn(new ResponseEntity<>("Successfully data updated!!!", HttpStatus.OK));

        mockMvc.perform(put("/hotel/updateByHotelId/" + hotel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(hotel)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(hotelService,times(1)).updateHotelDetails(any(),anyInt());
    }

    @Test
    void testSearchHotelsByLocation() throws Exception {
        when(hotelService.getHotelsByLocation(any())).thenReturn(hotels);

        mockMvc.perform(get("/hotel/searchByLocation/" + addressId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(hotelService,times(1)).getHotelsByLocation(any());
    }

    @Test
    void testSearchHotelsByName() throws Exception {
        when(hotelService.getHotelsByName(any())).thenReturn(hotels);

        mockMvc.perform(get("/hotel/searchByName/" + hotel.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(hotelService,times(1)).getHotelsByName(any());
    }

    @Test
    void testSearchHotelsById() throws Exception {
        when(hotelService.getHotelById(any())).thenReturn(Optional.of(hotel));

        mockMvc.perform(get("/hotel/searchByHotelId/" + hotel.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(hotelService,times(1)).getHotelById(anyInt());
    }

    @Test
    void testDeleteHotelById() throws Exception {
        when(hotelService.deleteHotelById(any())).thenReturn(true);

        mockMvc.perform(delete("/hotel/delete/" + hotel.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(hotelService,times(1)).deleteHotelById(anyInt());
    }
}

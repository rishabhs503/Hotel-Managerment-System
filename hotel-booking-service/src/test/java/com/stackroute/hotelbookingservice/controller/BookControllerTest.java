package com.stackroute.hotelbookingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.stackroute.hotelbookingservice.entities.Booking;
import com.stackroute.hotelbookingservice.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    BookingService bookingService;

    @InjectMocks
    BookingController bookingController;

    Booking booking;

    @BeforeEach
    public void setUp() {
        booking = new Booking();
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    public void addBooking() throws Exception {
        when(bookingService.saveBooking(any(), anyInt(), anyInt())).thenReturn(booking);

        mockMvc.perform(post("/booking/saveOneBooking/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(bookingService, times(1)).saveBooking(any(), anyInt(), anyInt());
    }

    @Test
    public void deleteBooking_success() throws Exception {
        when(bookingService.deleteBooking(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/booking/deleteBooking/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(bookingService, times(1)).deleteBooking(anyInt());
    }

    @Test
    public void getAllRecords_success() throws Exception {
        List<Booking> bookings = new ArrayList<>(Arrays.asList(booking, booking));
        when(bookingService.getBookings()).thenReturn(bookings);

        mockMvc.perform(MockMvcRequestBuilders.get("/booking/getBookings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(bookingService, times(1)).getBookings();
    }

    @Test
    public void getBookingById() throws Exception {
        when(bookingService.getBookingByID(anyInt())).thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/booking/getBooking/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(bookingService, times(1)).getBookingByID(anyInt());
    }

    @Test
    public void updateBooking() throws Exception {
        when(bookingService.updateBooking(any(), anyInt())).thenReturn(booking);

        mockMvc.perform(put("/booking/updateBooking/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(booking)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(bookingService, times(1)).updateBooking(any(), anyInt());
    }

    private static String jsonToString(final Object o) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(o);
            result = jsonContent;
            return result;

        } catch (JsonProcessingException e) {
            result = "JsonProcessingException";
        }
        return result;
    }
}

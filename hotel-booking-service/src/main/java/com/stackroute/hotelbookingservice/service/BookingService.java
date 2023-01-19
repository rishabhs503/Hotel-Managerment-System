package com.stackroute.hotelbookingservice.service;
import java.util.List;
import com.stackroute.hotelbookingservice.entities.Booking;
import com.stackroute.hotelbookingservice.exception.ResourceNotFoundException;

public interface BookingService {
		//for save the booking
		 Booking saveBooking(Booking booking, int userId, int hotelId);
		
		//for get booking by id
		Booking getBookingByID(int bookingId);
		
		//for get all bookings
		List<Booking> getBookings();
		
		//for delete bookings
		boolean deleteBooking(int bookingId)throws ResourceNotFoundException;
		
		//for updating booking
		Booking updateBooking(Booking booking,int bookingId);


}

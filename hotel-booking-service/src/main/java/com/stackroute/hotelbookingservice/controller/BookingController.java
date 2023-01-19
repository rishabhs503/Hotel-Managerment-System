package com.stackroute.hotelbookingservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stackroute.hotelbookingservice.entities.Booking;
import com.stackroute.hotelbookingservice.service.BookingService;

	@RestController
	@RequestMapping("/booking")
	public class BookingController {
	@Autowired
	BookingService bookingService;

	@PostMapping("/saveOneBooking/{userId}/{hotelId}")
	Booking saveBooking(@RequestBody Booking booking, @PathVariable("userId") int userId, @PathVariable("hotelId") int hotelId) {
		return bookingService.saveBooking(booking, userId, hotelId);
	}

	@GetMapping("/getBookings")
	List<Booking> getBookings(){
		return bookingService.getBookings();
		}

	@GetMapping("/getBooking/{id}")
	public Booking getBookingById(@PathVariable int id) {
		return bookingService.getBookingByID(id);
		}

	@PutMapping("/updateBooking/{bookingId}")
	public String upDatedBooking(@RequestBody Booking booking,@PathVariable int bookingId) {
		bookingService.updateBooking(booking,bookingId);
		return "user updated Successfully !!!";
		}

	@DeleteMapping("/deleteBooking/{id}")
	public String deleteBooking(@PathVariable int id) {
		bookingService.deleteBooking(id);
		return "deleted successfully";
		}
		
	}

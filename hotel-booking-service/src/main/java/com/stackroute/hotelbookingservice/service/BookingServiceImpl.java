package com.stackroute.hotelbookingservice.service;

import java.util.List;

import com.stackroute.hotelbookingservice.payloads.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stackroute.hotelbookingservice.configuration.Producer;
import com.stackroute.hotelbookingservice.entities.Booking;
import com.stackroute.hotelbookingservice.exception.ResourceNotFoundException;
import com.stackroute.hotelbookingservice.repository.BookingRepository;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    Producer producer;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.getDetails}")
    private String userUrl;

    @Value("${hotel.getDetails}")
    private String hotelUrl;

    @Override
    public Booking saveBooking(Booking booking, int userId, int hotelId) {
        //To get the data from user service and user service
        UserDTO user = restTemplate.getForObject(userUrl + userId, UserDTO.class);
        HotelDTO hotel = restTemplate.getForObject(hotelUrl + hotelId, HotelDTO.class);

        //To set the data booking
        booking.setUserName(user.getFirstName() + " " + user.getLastName());
        booking.setEmailId(user.getEmailId());
        booking.setContactNumber(user.getMobileNumber());
        booking.setHotelName(hotel.getName());
        booking.setAddress(hotel.getAddress().getCity() + ", " + hotel.getAddress().getState() + ", " + hotel.getAddress().getCountry());
        booking.setHotelReceptionNumber(hotel.getContacts().getPhoneNumber() + "");
        for (RoomDto room : hotel.getRooms()) {
            if (room.getRoomType().equals(booking.getRoomType())) {
                booking.setPriceOfRoom(room.getPrice());
                if (room.getNoOfRooms() >= booking.getNoOfRooms() && booking.getNoOfRooms() > 0) {
                    booking.setNoOfRooms(booking.getNoOfRooms());
                }
            }
        }
        booking.setTotalPrice(booking.getNoOfRooms() * booking.getNoOfDays() * booking.getPriceOfRoom());
        booking.setBookingConfirmed(false);

        //To send the data to payment service
        PaymentDTO paymentDto = new PaymentDTO();
        paymentDto.setBookingId(booking.getBookingId());
        paymentDto.setUserName(booking.getUserName());
        paymentDto.setEmailId(booking.getEmailId());
        paymentDto.setContactNumber(booking.getContactNumber());
        paymentDto.setTotalPrice(booking.getTotalPrice());
        producer.sendMessageToRabbitMq(paymentDto);

        Booking newBooking = bookingRepository.save(booking);

        return newBooking;

    }

    @Override
    public Booking getBookingByID(int bookingId) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            throw new ResourceNotFoundException("booking  is not found");
        } else {
            return booking;

        }

    }

    @Override
    public List<Booking> getBookings() throws ResourceNotFoundException {
        List<Booking> list = this.bookingRepository.findAll();
        if (list.size() <= 0) {
            throw new ResourceNotFoundException("booking  is not found");
        } else {

            return list;
        }
    }

    @Override
    public boolean deleteBooking(int bookingId) throws ResourceNotFoundException {
        boolean flag = false;
        Booking booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            throw new ResourceNotFoundException("booking is not found");
        } else {
            bookingRepository.delete(booking);
            flag = true;
        }
        return flag;

    }

    @Override
    public Booking updateBooking(Booking booking, int bookingId) {
        Booking existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking == null) {
            throw new ResourceNotFoundException("booking is not found");
        } else {
            existingBooking = bookingRepository.save(booking);
            //To send the data to email service through rabbitmq
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setEmail(booking.getEmailId());
            emailDTO.setBookingId(bookingId);
            emailDTO.setNoOfDays(booking.getNoOfDays());
            emailDTO.setFirstName(booking.getUserName());
            emailDTO.setRoomType(booking.getRoomType());
            emailDTO.setHotelName(booking.getHotelName());
            emailDTO.setAddress(booking.getAddress());
            emailDTO.setCheckIn(booking.getCheckInDate() + ":" + booking.getCheckInTime());
            emailDTO.setCheckOut(booking.getCheckOutDate() + ":" + booking.getCheckOutTime());
            emailDTO.setTotalPrice(booking.getTotalPrice());
            emailDTO.setHotelReceptionNumber(booking.getHotelReceptionNumber());
            producer.sendMessageToRabbitMqEmail(emailDTO);

            //To send the data to sms service through rabbitmq
            SmsDTO smsDTO = new SmsDTO();
            smsDTO.setUserName(booking.getUserName());
            smsDTO.setContactNumber(booking.getContactNumber());
            smsDTO.setBookingId(booking.getBookingId());
            smsDTO.setHotelName(booking.getHotelName());
            smsDTO.setCheckInDate(booking.getCheckInDate());
            smsDTO.setCheckOutDate(booking.getCheckOutDate());
            smsDTO.setRoomType(booking.getRoomType());
            smsDTO.setNumberOfGuests(booking.getNumberOfGuests());
            smsDTO.setTotalPrice(booking.getTotalPrice());
            producer.sendMessageToRabbitMqSms(smsDTO);
        }
        return existingBooking;
    }
}

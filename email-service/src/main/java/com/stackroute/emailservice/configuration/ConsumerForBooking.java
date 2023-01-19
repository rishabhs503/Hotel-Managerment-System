package com.stackroute.emailservice.configuration;

import com.stackroute.emailservice.exception.EmailException;
import com.stackroute.emailservice.model.UserDetails;
import com.stackroute.emailservice.payload.EmailDto;
import com.stackroute.emailservice.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerForBooking {

    @Autowired
    private EmailService emailService;

    private static final String QUEUE_TO_GET_BOOKING = "queue_to_get_booking";
    private static final String QUEUE_TO_GET_CANCELLATION = "queue_to_get_cancellation";

    @RabbitListener(queues = QUEUE_TO_GET_BOOKING)
    public void bookingSuccess(EmailDto emailDto) throws EmailException {
        UserDetails userDetails = new UserDetails();
        System.out.println(emailDto.getEmail()); // Testing purpose only
        userDetails.setFirstName(emailDto.getFirstName());
        userDetails.setRecieverMailAdd(emailDto.getEmail());
        userDetails.setOrderId(emailDto.getBookingId());
        userDetails.setHotelName(emailDto.getHotelName());
        userDetails.setTotalPrice(emailDto.getTotalPrice());
        userDetails.setNoOfDays(emailDto.getNoOfDays());
        userDetails.setRoomType(emailDto.getRoomType());
        userDetails.setCheckIn(emailDto.getCheckIn());
        userDetails.setCheckOut(emailDto.getCheckOut());
        userDetails.setHotelReceptionNumber(emailDto.getHotelReceptionNumber());
        userDetails.setAddress(emailDto.getAddress());

        emailService.bookingMail(userDetails);
    }

    @RabbitListener(queues = QUEUE_TO_GET_CANCELLATION)
    public void bookingCancellation(EmailDto emailDto) throws EmailException {
        UserDetails userDetails = new UserDetails();
        System.out.println(emailDto.getEmail()); // Testing purpose only
        userDetails.setFirstName(emailDto.getFirstName());
        userDetails.setRecieverMailAdd(emailDto.getEmail());
        userDetails.setOrderId(emailDto.getBookingId());
        userDetails.setHotelName(emailDto.getHotelName());
        emailService.cancellationMail(userDetails);
    }
}

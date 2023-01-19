package com.stackroute.hotelbookingservice.configuration;

import com.stackroute.hotelbookingservice.payloads.PaymentReceiverDTO;
import com.stackroute.hotelbookingservice.service.BookingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.stackroute.hotelbookingservice.entities.Booking;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    @Autowired
    BookingService bookingService;
	
	@RabbitListener(queues="payment_details_queue")
	public void getUserDtoFromRabbitMq(PaymentReceiverDTO paymentReceiverDTO) {
        System.out.println("Payment Data Received: "+paymentReceiverDTO);
//        Booking booking = new Booking();
        Booking booking = bookingService.getBookingByID(paymentReceiverDTO.getBookingId());
        booking.setPaymentId(paymentReceiverDTO.getPaymentId());
        booking.setPaymentSignature(paymentReceiverDTO.getPaymentSignature());
        booking.setOrderId(paymentReceiverDTO.getOrderId());
        booking.setBookingConfirmed(true);
        bookingService.updateBooking(booking,paymentReceiverDTO.getBookingId());

        System.out.println(booking);
    }
}

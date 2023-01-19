package com.stackroute.smsservice.services;

import com.stackroute.smsservice.dto.*;
import com.stackroute.smsservice.model.MessageDetails;
import com.stackroute.smsservice.model.UserDetails;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    private final SmsService smsService;

    private MessageDetails messageDetails;
    private UserDetails userDetails;

    @Autowired
    public ConsumerService(SmsService smsService) {
        this.smsService = smsService;
    }

    @RabbitListener(queues = "${queue.name.userVerification}")
    public void userOtpVerification(UserVerficationDto userVerficationDto) {
        System.out.print(userVerficationDto);
        userDetails = new UserDetails();
        messageDetails = new MessageDetails();
        messageDetails.setOpt(userVerficationDto.getOtp());
        messageDetails.setUrl("http://localhost:9001/authentication/otp/"+userVerficationDto.getUserId()+"/"+userVerficationDto.getOtp());
        userDetails.setRequestType("success-verified-user");
        userDetails.setFirstname(userVerficationDto.getFirstName());
        userDetails.setNumbers(userVerficationDto.getMobileNumber());
        userDetails.setDetails(messageDetails);
        smsService.sendSms(userDetails);
    }

    @RabbitListener(queues = "${queue.name.userForgetPassword}")
    public void userForgetPassword(UserForgetPasswordDto userForgetPasswordDto) {
        userDetails = new UserDetails();
        messageDetails = new MessageDetails();
        messageDetails.setOpt(userForgetPasswordDto.getOtp());
        messageDetails.setUrl("http://localhost:9001/authentication/forgot-password/"+userForgetPasswordDto.getMobileNumber());
        userDetails.setRequestType("forget-password");
        userDetails.setFirstname(userForgetPasswordDto.getFirstName());
        userDetails.setNumbers(userForgetPasswordDto.getMobileNumber());
        userDetails.setDetails(messageDetails);
        smsService.sendSms(userDetails);
    }

    @RabbitListener(queues = "${queue.name.userHotelBooking}")
    public void bookingConfirmation(BookingConfirmationDto bookingConfirmationDto) {
        userDetails = new UserDetails();
        messageDetails = new MessageDetails();

        messageDetails.setBookingId(bookingConfirmationDto.getBookingId());
        messageDetails.setRoomId(bookingConfirmationDto.getRoomId());
        messageDetails.setNumberPerson(bookingConfirmationDto.getNumberOfGuests());
        messageDetails.setHotelName(bookingConfirmationDto.getHotelName());
        messageDetails.setCheckIn(bookingConfirmationDto.getCheckInDate());
        messageDetails.setCheckout(bookingConfirmationDto.getCheckOutDate());
        messageDetails.setRoomType(bookingConfirmationDto.getRoomType());
        messageDetails.setPrice(bookingConfirmationDto.getTotalPrice());
        messageDetails.setNoOfdays(bookingConfirmationDto.getNoOfDays());

        userDetails.setNumbers(bookingConfirmationDto.getContactNumber());
        userDetails.setFirstname(bookingConfirmationDto.getUserName());
        userDetails.setDetails(messageDetails);

        userDetails.setRequestType("booking-payment-success");

        System.out.print(bookingConfirmationDto.toString());
        smsService.sendSms(userDetails);
    }

    @RabbitListener(queues = "${queue.name.hotelPropertyRegistration}")
    public void hotelPropertyRegister(HotelPropertyRegisterDto hotelPropertyRegisterDto) {
        userDetails = new UserDetails();
        messageDetails = new MessageDetails();
        messageDetails.setHotelName(hotelPropertyRegisterDto.getHotelName());
        userDetails.setFirstname(hotelPropertyRegisterDto.getFirstName());
        userDetails.setNumbers(hotelPropertyRegisterDto.getContact_number());
        userDetails.setDetails(messageDetails);
        userDetails.setRequestType("register-property-success");

        System.out.print(hotelPropertyRegisterDto.toString());
        smsService.sendSms(userDetails);
    }
}


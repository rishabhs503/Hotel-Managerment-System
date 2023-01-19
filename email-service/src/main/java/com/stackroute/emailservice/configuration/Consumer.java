package com.stackroute.emailservice.configuration;

import com.stackroute.emailservice.exception.EmailException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.emailservice.model.UserDetails;
import com.stackroute.emailservice.payload.EmailDto;
import com.stackroute.emailservice.service.EmailService;

@Component
public class Consumer {
	
	@Autowired
	private EmailService emailService;
	
	private static final String QUEUE_TO_GET_EMAILER = "queue_to_get_emailer";
	private static final String QUEUE_TO_GET_EMAILER_SUCCESS = "queue_to_get_emailer_success";
	private static final String QUEUE_TO_GET_EMAILER_RESET = "queue_to_get_emailer_reset";
	private static final String QUEUE_TO_GET_EMAILER_RESET_SUCCESS = "queue_to_get_emailer_reset_success";

	@RabbitListener(queues = QUEUE_TO_GET_EMAILER)
	public void getDataFromRabbitMQ(EmailDto emailDto) throws EmailException {
		UserDetails userDetails = new UserDetails();
		System.out.println(emailDto.getEmail()); // Testing purpose only
		userDetails.setOtp(emailDto.getOtp());
		userDetails.setRecieverMailAdd(emailDto.getEmail());
		userDetails.setLink("http://localhost:"+ emailDto.getPort()+"/verification/otp/"+ emailDto.getUserId()+"/"+ emailDto.getOtp());
		emailService.otpMail(userDetails);
	}

	@RabbitListener(queues = QUEUE_TO_GET_EMAILER_SUCCESS)
	public void welcome(EmailDto emailDto) throws EmailException {
		UserDetails userDetails = new UserDetails();
		System.out.println(emailDto.getEmail()); // Testing purpose only
		userDetails.setOtp(emailDto.getOtp());
		userDetails.setFirstName(emailDto.getFirstName());
		userDetails.setRecieverMailAdd(emailDto.getEmail());
		emailService.welcomeMail(userDetails);

	}

	@RabbitListener(queues = QUEUE_TO_GET_EMAILER_RESET)
	public void Reset(EmailDto emailDto) throws EmailException {
		UserDetails userDetails = new UserDetails();
		System.out.println(emailDto.getEmail()); // Testing purpose only
		userDetails.setRecieverMailAdd(emailDto.getEmail());
		userDetails.setOtp(emailDto.getOtp());
		emailService.forgetMail(userDetails);

	}

	@RabbitListener(queues = QUEUE_TO_GET_EMAILER_RESET_SUCCESS)
	public void ResetSuccess(EmailDto emailDto) throws EmailException {
		UserDetails userDetails = new UserDetails();
		System.out.println(emailDto.getEmail()); // Testing purpose only
		userDetails.setRecieverMailAdd(emailDto.getEmail());
		emailService.resetSuccessMail(userDetails);

	}

}

package com.stackroute.emailservice.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.stackroute.emailservice.exception.EmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.stackroute.emailservice.model.UserDetails;

@Slf4j
@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

	private static final String regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

	/**
	 * This Method Process Data/Parameter for Sending WELCOME mail
	 * 
	 * @param 'firstName', recieverMailAdd
	 */
	@Override
	public ResponseEntity<String> welcomeMail(UserDetails userDetail) throws EmailException {

		String htmlMsg = "Hi " + userDetail.getFirstName() + "!! Welcome to EaseMyStay, <br> Your Email Id is successfully verified now <br> <font color='#63B4FC'>Happy Hoteling :)</font>";
		String subject = "registration successful";
		if(userDetail.getRecieverMailAdd().isEmpty() || !userDetail.getRecieverMailAdd().matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(userDetail.getRecieverMailAdd());
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
			log.info("From EmailServiceImpl--> In welcome mail method: email sent successfully...");
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("Sending Email Failed!!");
		}
		return new ResponseEntity<>("Email Sent Successfully...", HttpStatus.OK);
	}

	/**
	 * This Method Process Data/Parameter for Sending BOOKING DETAILS
	 * @param 'firstName', recieverMailAdd, orderId, HotelName, Amount
	 */
	@Override
	public ResponseEntity<String> bookingMail(UserDetails bookingDetails) throws EmailException {
		if(bookingDetails.getRecieverMailAdd().isEmpty() || !bookingDetails.getRecieverMailAdd().matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}
		String subject = "Booking Inovice for order id : " + bookingDetails.getOrderId();
		String htmlMsg = "<h3>Greetings " + bookingDetails.getFirstName() + " !!</h3>"
				+ " Booking Confirmed !! We are pleased to inform you that Your Booking " + bookingDetails.getOrderId() + " at Hotel " + bookingDetails.getHotelName()
				+ " has been Confirmed <br> Reservation details: <br>"
				+ " Total amount paid : ₹" + bookingDetails.getTotalPrice() + " For "+bookingDetails.getNoOfDays()+" Days <br>"
				+ "Room Type : "+ bookingDetails.getRoomType()+"<br>"
				+ "Your Check IN : "+bookingDetails.getCheckIn()+"<br>"
				+ "Your Check Out : "+bookingDetails.getCheckOut()+"<br>"
				+ "Hotels address : "+bookingDetails.getAddress()+"<br>"
				+ "Reception Number : "+bookingDetails.getHotelReceptionNumber()+"<br> Sincerely awaiting your visit<br>"// +bookingDetails.getHtmlBody()
				+ "<font color='#63B4FC'>Happy Hoteling :)</font>";

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(bookingDetails.getRecieverMailAdd());
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
			log.info("From EmailServiceImpl--> In bookingMail method: email sent successfully...");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<>("mail sent successfully...", HttpStatus.OK);
	}

	/**
	 * This Method Process Data/Parameter for Sending OTP
	 * 
	 * @param 'recevierMailAdd', Otp
	 */
	@Override
	public ResponseEntity<String> otpMail(UserDetails details) throws EmailException {

		String subject = "EaseMyStay : Confirmation mail";
		String htmlMsg = "We're Happy to see you on EaseMyStay, To start Exploring Hotel Across India, Please confirm your email address <br><a href='"+details.getLink()+"'><button style='background-color: Red;\n" +
				"  border: none;\n" +
				"  color: white;\n" +
				"  padding: 10px;\n" +
				"  text-align: center;\n" +
				"  border-radius: 12px;\n" +
				"  text-decoration: none;\n" +
				"  display: inline-block;\n" +
				"  font-size: 16px;\n" +
				"  margin: 4px 2px;\n" +
				"  cursor: pointer;'>Verify Now</button></a><br> Or Enter this OTP : "+ details.getOtp()+"<br> <font color='#63B4FC'>Happy Hoteling :)</font>";

		if(details.getRecieverMailAdd().isEmpty() || !details.getRecieverMailAdd().matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}


		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(details.getRecieverMailAdd());
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
			log.info("From EmailServiceImpl--> In otpMail method: email sent successfully...");
		} catch (MessagingException e) {
			System.out.println("Mail Send Failed due to following exception occured :");
			e.printStackTrace();
		}
		return new ResponseEntity<>("mail sent successfully...", HttpStatus.OK);
	}

	/**
	 * This Method Process Data/Parameter for Sending OTP for RESETING PASSWORD
	 * 
	 * @param 'recieverMailAdd', Otp
	 */
	@Override
	public ResponseEntity<String> forgetMail(UserDetails details) throws EmailException {

		String subject = "EaseMyStay : Reset Password";
		String htmlMsg = "The Otp for Reseting account password is : "+ details.getOtp()+"<br> Or <a href='"+details.getLink()+"'>Click Here</a><br><font color='#63B4FC'>Happy Hoteling :)</font>";

		if(details.getRecieverMailAdd().isEmpty() || !details.getRecieverMailAdd().matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}


		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(details.getRecieverMailAdd());
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
			log.info("From EmailServiceImpl--> In forgetMail method: email sent successfully...");
		} catch (MessagingException e) {
			System.out.println("Mail Send Failed due to following exception occured :");
			e.printStackTrace();
		}
		return new ResponseEntity<>("mail sent successfully...", HttpStatus.OK);
	}

	/**
	 * This Method Process Data/Parameter for Sending ALERT about PASSWORD UPDATE
	 * 
	 * @param 'recieverMailAdd'
	 */
	@Override
	public ResponseEntity<String> resetSuccessMail(UserDetails details) throws EmailException {

		String subject = "EaseMyStay : Password Updated";
		String htmlMsg = "Password for your account has been changed sucessfully<br><font color='#63B4FC'>Happy Hoteling :)</font>";

		if(details.getRecieverMailAdd().isEmpty() || !details.getRecieverMailAdd().matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}


		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(details.getRecieverMailAdd());
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
			log.info("From EmailServiceImpl--> In resetSuccessMail method: email sent successfully...");
		} catch (MessagingException e) {
			System.out.println("Mail Send Failed due to following exception occured :");
			e.printStackTrace();
		}
		return new ResponseEntity<>("mail sent successfully...", HttpStatus.OK);
	}

	/**
	 * This Method Process Data/Parameter for Sending Update to Inform about
	 * UNSUBSCRIPTION of OFFER MAILS
	 * 
	 * @param 'recieverMailAdd', link
	 */
	@Override
	public ResponseEntity<String> unSubMail(UserDetails details) throws EmailException {

		String subject = "Unsubscribed !!";
		String htmlMsg = "You have unsubscribed to the offer alerts";

		if(details.getRecieverMailAdd().isEmpty() || !details.getRecieverMailAdd().matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(details.getRecieverMailAdd());
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
			log.info("From EmailServiceImpl--> In unSubMail method: email sent successfully...");
		} catch (MessagingException e) {
			System.out.println("Mail Send Failed due to following exception occured :");
			e.printStackTrace();
		}
		return new ResponseEntity<>("mail sent successfully...", HttpStatus.OK);
	}

	/**
	 *This Method is for Braking up Data for Offer Method
	 *@param 'usersMailIds', usersfirstName, htmlBody
	 */
	@Override
	public String dataOfferMail(UserDetails userDetails) {
		try {
			String subject = userDetails.getSubject();
			for (int i = 0; i < userDetails.getUsersMailIds().size(); i++) {
				String to = userDetails.getUsersMailIds().get(i);
				String firstname = userDetails.getUsersfirstName().get(i);
				String htmlBody = "Hey " + firstname + "! <br>" + userDetails.getHtmlBody() + "<br><font color='#63B4FC'>Happy Hoteling :)</font><br><small> click here to<a href='"
						+ userDetails.getLink() + "'/> unsubcribe offer mails</a></small>";
				
				offerMail(to, subject, htmlBody);
			}
			log.info("From EmailServiceImpl--> In dataOfferMail method: email forward further to OfferMail successfully...");
			return "forwarded Successfully";
		} catch (Exception e) {
//			System.out.println(e);
			e.printStackTrace();
			return "Sending Failed !! One or more than One mailId or firstname are null";
		}
	}

	/**
	 * This Method Process Data/Parameter for Sending OFFER MAILS to MULTIPLE/BULK
	 * USERS
	 */
	public void offerMail(String to, String subject, String htmlMsg) throws EmailException {

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		if(to.isEmpty() || !to.matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(to);
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
		} catch (MessagingException msgException) {
			System.out.println("Mail Send Failed due to following exception occured :");
			msgException.printStackTrace();
		}

	}

	/**
	 * This Method Process Data/Parameter for Sending OTP for DUAL FACTOR
	 * AUTHENTICATION
	 * 
	 * @param 'recieverMailAdd', firstName, Otp
	 */
	@Override
	public ResponseEntity<String> daulFactorAuth(UserDetails details) throws EmailException {

		String subject = "Dual Factor Authentication";
		String htmlMsg = "Hey " + details.getFirstName() + "<br>Otp for your recent login is : " + details.getOtp()+"<br><font color='#63B4FC'>Happy Hoteling :)</font>";

		if(details.getRecieverMailAdd().isEmpty() || !details.getRecieverMailAdd().matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(details.getRecieverMailAdd());
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
			log.info("From EmailServiceImpl--> In dualFactorAuth method: email sent successfully...");
		} catch (MessagingException e) {
			System.out.println("Mail Send Failed due to following exception occured :");
			e.printStackTrace();
		}
		return new ResponseEntity<>("mail sent successfully...", HttpStatus.OK);
	}

	/**
	 * This Method Process Data/Parameter for Sending update mail of BOOKING
	 * CANCELLATION
	 * 
	 * @param 'recieverMailAdd', firstName, oderId, hotelName
	 */
	@Override
	public ResponseEntity<String> cancellationMail(UserDetails details) throws EmailException {

		String subject = "EaseMyStay : Booking Cancelled";
		String htmlMsg = "Hello " + details.getFirstName() + " <br>Your Booking for order id : " + details.getOrderId()
				+ " at " + details.getHotelName() + " has been cancelled <br><font color='#63B4FC'>Happy Hoteling :)</font>";

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		if(details.getRecieverMailAdd().isEmpty() || !details.getRecieverMailAdd().matches(regexp) ){
			log.info("From EmailServiceImpl--> In welcomeMail method: Receivers email is invalid or empty!!!");
			throw new EmailException("Receivers email Is invalid or empty!!!");
		}

		try {
			helper.setText(htmlMsg, true);
			helper.setTo(details.getRecieverMailAdd());
			helper.setSubject(subject);
			emailSender.send(mimeMessage);
			log.info("From EmailServiceImpl--> In CamcellationMail method: email sent successfully...");
		} catch (MessagingException e) {
			System.out.println("Mail Send Failed due to following exception occured :");
			e.printStackTrace();
		}
		return new ResponseEntity<>("mail sent successfully...", HttpStatus.OK);
	}

}

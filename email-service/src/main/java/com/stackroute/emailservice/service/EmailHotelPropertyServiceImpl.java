package com.stackroute.emailservice.service;

import com.stackroute.emailservice.exception.EmailException;
import com.stackroute.emailservice.model.HotelPropertyDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailHotelPropertyServiceImpl implements EmailHotelPropertyService{

    private static final String regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public ResponseEntity<String> newHotelMail(HotelPropertyDetails details) throws EmailException {
        String htmlMsg = "<center><h2> Welcome </h2> Dear "+details.getFirstName()+"<br> We're excited to have you with us!<br>Your New hotel with name "+ details.getHotelName() + " has been added ,<br>Just a reminder add images for better engagement and reach <br><hr style='width:50%;text-align:center;margin-center:0'>"+details.getHotelName()+" | 123 Street Fifth Avenue Pune | <br>1800200XXX<br><font color='#63B4FC'>Happy Hoteling :)</font></center>";
        String subject = "Hotel added successfully";
        if(details.getEmailID().isEmpty() || !details.getEmailID().matches(regexp) ){
            log.info("From EmailHotelPropertyServiceImpl--> In newHotelMail method: Receivers email is invalid or empty!!!");
            throw new EmailException("Receivers email Is invalid or empty!!!");
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setText(htmlMsg, true);
            helper.setTo(details.getEmailID());
            helper.setSubject(subject);
            mailSender.send(mimeMessage);
            log.info("From EmailHotelPropertyServiceImpl--> In newHotelMail method: email sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Sending Email Failed!!");
        }
        return new ResponseEntity<>("Email Sent Successfully...", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateHotelMail(HotelPropertyDetails details) throws EmailException {
        String htmlMsg = "Hi " + details.getFirstName() + "!!, <br> Details of hotel "+ details.getHotelName( )+ " are updated & Same will be reflected from now , <br><font color='#63B4FC'>Happy Hoteling :)</font>";
        String subject = "Hotel details updated";
        if(details.getEmailID().isEmpty() || !details.getEmailID().matches(regexp) ){
            log.info("From EmailHotelPropertyServiceImpl--> In updateHotelMail method: Receivers email is invalid or empty!!!");
            throw new EmailException("Receivers email Is invalid or empty!!!");
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setText(htmlMsg, true);
            helper.setTo(details.getEmailID());
            helper.setSubject(subject);
            mailSender.send(mimeMessage);
            log.info("From EmailHotelPropertyServiceImpl--> In updateHotelMail method: email sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Sending Email Failed!!");
        }
        return new ResponseEntity<>("Email Sent Successfully...", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteHotelMail(HotelPropertyDetails details) throws EmailException {
        String htmlMsg = "Hi " + details.getFirstName() + "!!, <br> your hotel "+ details.getHotelName( )+ " has been deleted on your request , <br>We're sorry to see you go";
        String subject = "Hotel removed";
        if(details.getEmailID().isEmpty() || !details.getEmailID().matches(regexp) ){
            log.info("From EmailHotelPropertyServiceImpl--> In deleteHotelMail method: Receivers email is invalid or empty!!!");
            throw new EmailException("Receivers email Is invalid or empty!!!");
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setText(htmlMsg, true);
            helper.setTo(details.getEmailID());
            helper.setSubject(subject);
            mailSender.send(mimeMessage);
            log.info("From EmailHotelPropertyServiceImpl--> In deleteHotelMail method: email sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Sending Email Failed!!");
        }
        return new ResponseEntity<>("Email Sent Successfully...", HttpStatus.OK);
    }
}

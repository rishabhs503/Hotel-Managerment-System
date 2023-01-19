package com.stackroute.emailservice.configuration;

import com.stackroute.emailservice.exception.EmailException;
import com.stackroute.emailservice.model.HotelPropertyDetails;
import com.stackroute.emailservice.payload.EmailDto;
import com.stackroute.emailservice.service.EmailHotelPropertyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerForHotelProperty {

    @Autowired
    private EmailHotelPropertyService propertyService;

    private static final String QUEUE_TO_GET_NEW_HOTEL ="queue_to_get_new_hotel";
    private static final String QUEUE_TO_GET_UPDATE_HOTEL ="queue_to_get_update_hotel";
    private static final String QUEUE_TO_GET_DELETE_HOTEL ="queue_to_get_delete_hotel";

    @RabbitListener(queues = QUEUE_TO_GET_NEW_HOTEL)
    public void newHotelMail(EmailDto emailDto) throws EmailException {
        HotelPropertyDetails userDetails = new HotelPropertyDetails();

        userDetails.setFirstName(emailDto.getFirstName());
        userDetails.setEmailID(emailDto.getEmail());
        userDetails.setHotelName(emailDto.getHotelName());
        propertyService.newHotelMail(userDetails);
    }

    @RabbitListener(queues = QUEUE_TO_GET_UPDATE_HOTEL)
    public void updateHotelMail(EmailDto emailDto) throws EmailException {
        HotelPropertyDetails userDetails = new HotelPropertyDetails();

        userDetails.setFirstName(emailDto.getFirstName());
        userDetails.setEmailID(emailDto.getEmail());
        userDetails.setHotelName(emailDto.getHotelName());
        propertyService.updateHotelMail(userDetails);
    }

    @RabbitListener(queues = QUEUE_TO_GET_DELETE_HOTEL)
    public void deleteHotelMail(EmailDto emailDto) throws EmailException {
        HotelPropertyDetails userDetails = new HotelPropertyDetails();

        userDetails.setFirstName(emailDto.getFirstName());
        userDetails.setEmailID(emailDto.getEmail());
        userDetails.setHotelName(emailDto.getHotelName());
        propertyService.deleteHotelMail(userDetails);
    }
}

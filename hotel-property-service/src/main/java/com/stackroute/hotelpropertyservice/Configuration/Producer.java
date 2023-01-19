package com.stackroute.hotelpropertyservice.Configuration;

import com.stackroute.hotelpropertyservice.payload.EmailDto;
import com.stackroute.hotelpropertyservice.payload.SmsDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final static String ROUTING_KEY_FOR_NEW_HOTEL = "property_routing_to_new_hotel_mail";
    private final static String ROUTING_KEY_FOR_UPDATE_HOTEL = "property_routing_to_update_hotel_mail";
    private final static String ROUTING_KEY_FOR_DELETE_HOTEL = "property_routing_to_delete_hotel_mail";
    private final static String ROUTING_KEY_FOR_SMS_ADD_HOTEL = "property_routing_to_add_hotel_sms";
    private final static String ROUTING_KEY_FOR_SMS_UPDATE_HOTEL = "property_routing_to_update_hotel_sms";
    private final static String ROUTING_KEY_FOR_SMS_DELETE_HOTEL = "property_routing_to_delete_hotel_sms";

    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    public void sendMessageToRabbitMqForEmailNew(EmailDto emailDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_NEW_HOTEL,emailDTO);

    }
    public void sendMessageToRabbitMqForEmailUpdate(EmailDto emailDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_UPDATE_HOTEL,emailDTO);

    }
    public void sendMessageToRabbitMqForEmailDelete(EmailDto emailDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_DELETE_HOTEL,emailDTO);

    }
    public void sendMessageToRabbitMqForSmsNew(SmsDto smsDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_SMS_ADD_HOTEL,smsDTO);
    }

    public void sendMessageToRabbitMqForSmsUpdate(SmsDto smsDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_SMS_UPDATE_HOTEL,smsDTO);
    }

    public void sendMessageToRabbitMqForSmsDelete(SmsDto smsDto){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_SMS_DELETE_HOTEL,smsDto);

    }


}

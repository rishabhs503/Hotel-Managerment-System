package com.stackroute.userprofileservice.configuration;


import com.stackroute.userprofileservice.payload.EmailDto;
import com.stackroute.userprofileservice.payload.SmsDto;
import com.stackroute.userprofileservice.payload.UserDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final static String ROUTING_KEY_FOR_SAVE_USER = "user_routing_to_save_user";
    private final static String ROUTING_KEY_FOR_UPDATE_USER = "user_routing_to_update_user";
    private final static String ROUTING_KEY_FOR_DELETE_USER = "user_routing_to_delete_user";
    private final static String ROUTING_KEY_FOR_EMAIL = "user_routing_to_email";
    private final static String ROUTING_KEY_FOR_EMAIL_SUCCESS = "user_routing_to_email_success";
    private final static String ROUTING_KEY_FOR_EMAIL_RESET = "user_routing_to_email_reset";
    private final static String ROUTING_KEY_FOR_EMAIL_RESET_SUCCESS = "user_routing_to_email_reset_success";
    private final static String ROUTING_KEY_FOR_SMS_Registration = "user_routing_to_SMS_Registration";
    private final static String ROUTING_KEY_FOR_SMS_Forgot_Password = "user_routing_to_SMS_Forgot_Password";




    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    public void sendMessageToRabbitMqToSaveUser(UserDto userDTO) {
        rabbitTemplate.convertAndSend(directExchange.getName(), ROUTING_KEY_FOR_SAVE_USER, userDTO);
    }

    public void sendMessageToRabbitMqToDeleteUser(String emailId ) {
        rabbitTemplate.convertAndSend(directExchange.getName(), ROUTING_KEY_FOR_DELETE_USER , emailId);
    }

    public void sendMessageToRabbitMqToUpdateUser(UserDto userDTO) {
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_UPDATE_USER, userDTO);
    }

    public void sendMessageToRabbitMqForEmail(EmailDto emailDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_EMAIL,emailDTO);

    }

    public void sendMessageToRabbitMqForEmailSuccess(EmailDto emailDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_EMAIL_SUCCESS,emailDTO);

    }

    public void sendMessageToRabbitMqForEmailReset(EmailDto emailDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_EMAIL_RESET,emailDTO);

    }

    public void sendMessageToRabbitMqForEmailResetSuccess(EmailDto emailDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_EMAIL_RESET_SUCCESS,emailDTO);

    }

    public void sendMessageToRabbitMqForSMSRegistration(SmsDto smsDto){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_SMS_Registration,smsDto);

    }

    public void sendMessageToRabbitMqForSMSForgotPassword(SmsDto smsDto){
        rabbitTemplate.convertAndSend(directExchange.getName(),ROUTING_KEY_FOR_SMS_Forgot_Password,smsDto);

    }



}






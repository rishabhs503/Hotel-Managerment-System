package com.stackroute.hotelbookingservice.configuration;
import com.stackroute.hotelbookingservice.payloads.EmailDTO;
import com.stackroute.hotelbookingservice.payloads.SmsDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.stackroute.hotelbookingservice.payloads.PaymentDTO;

@Component
public class Producer {

	
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void sendMessageToRabbitMq(PaymentDTO paymentDto)
    {
        rabbitTemplate.convertAndSend(exchange.getName(),"payment_routing_key",paymentDto);
       
    }
    public void sendMessageToRabbitMqEmail(EmailDTO emailDto)
    {
        System.out.println(emailDto);

        rabbitTemplate.convertAndSend(exchange.getName(),"booking_routing_to_booking",emailDto);

    }
    public void sendMessageToRabbitMqSms(SmsDTO smsDtO)
    {
        rabbitTemplate.convertAndSend(exchange.getName(),"user_hotel_booking",smsDtO);

    }

}

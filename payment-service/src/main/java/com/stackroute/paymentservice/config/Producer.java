package com.stackroute.paymentservice.config;

import com.stackroute.paymentservice.payloads.PaymentResponseDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Producer {

    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void sendMessageToRabbitMq(PaymentResponseDTO paymentResponseDTO)
    {
        rabbitTemplate.convertAndSend(exchange.getName(),"payment_response_routing_key", paymentResponseDTO);
    }
}

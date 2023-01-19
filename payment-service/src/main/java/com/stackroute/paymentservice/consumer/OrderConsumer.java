package com.stackroute.paymentservice.consumer;

import com.stackroute.paymentservice.model.OrderRequest;
import com.stackroute.paymentservice.payloads.PaymentDTO;
import com.stackroute.paymentservice.service.OrderServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @Autowired
    private OrderServiceImpl orderServiceImpl ;

    @RabbitListener(queues = "payment_details_queue")
    public void consumeMessageFromQueue(PaymentDTO paymentDTO) {
        System.out.println("Payment DTO "+paymentDTO);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(paymentDTO.getBookingId());
        orderRequest.setCustomerName(paymentDTO.getUserName());
        orderRequest.setPhoneNumber(paymentDTO.getContactNumber());
        orderRequest.setEmail(paymentDTO.getEmailId());
        orderRequest.setAmount(paymentDTO.getTotalPrice());
        orderServiceImpl.saveOrder(orderRequest);
    }
}

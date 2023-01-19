package com.stackroute.paymentservice.service;

import com.stackroute.paymentservice.exception.ResourceNotFoundException;
import com.stackroute.paymentservice.model.OrderRequest;
import com.stackroute.paymentservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderRequest saveOrder(OrderRequest orderRequest) {
        return orderRepository.save(orderRequest);
    }

    @Override
    public String deleteOrderById(int id) {
        OrderRequest bookings = orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("order ","id",id));
        orderRepository.delete(bookings);
        return "Booking deleted";
    }

    @Override
    public List<OrderRequest> getOrders() {
        return (List<OrderRequest>)orderRepository.findAll();
    }
}

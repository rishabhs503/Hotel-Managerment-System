package com.stackroute.paymentservice.service;

import com.stackroute.paymentservice.model.OrderRequest;

import java.util.List;

public interface OrderService {

    OrderRequest saveOrder(OrderRequest orderRequest);

    //for deleting order by id
    String deleteOrderById(int id);

    //for get all orders
    List<OrderRequest> getOrders();
}

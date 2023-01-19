package com.stackroute.paymentservice.repository;

import com.stackroute.paymentservice.model.OrderRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderRequest, Integer> {

}

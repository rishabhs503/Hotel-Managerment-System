package com.stackroute.paymentservice.service;

import com.stackroute.paymentservice.model.OrderRequest;
import com.stackroute.paymentservice.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;
    OrderRequest orderRequest;

    private int DUMMY_ID = 1;
    private String DUMMY_NAME = "DUMMY_NAME";
    private String DUMMY_EMAIL = "DUMMYMAIL@MAIL.COM";
    private String DUMMY_NUMBER = "1234567890";
    private Double DUMMY_AMOUNT = 1000.00;

    @BeforeEach
    public void setUp() {
        orderRequest = new OrderRequest(DUMMY_ID, DUMMY_NAME, DUMMY_EMAIL, DUMMY_NUMBER, DUMMY_AMOUNT);
    }

    @AfterEach
    public void tearDown() {
        orderRequest = null;
    }

    @Test
    public void saveOrderTest_ReturnTrue() {
        Mockito.when(orderRepository.save(orderRequest)).thenReturn(orderRequest);
        Assertions.assertEquals(orderRequest, orderService.saveOrder(orderRequest));
        Mockito.verify(orderRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void deleteOrderById_ReturnsTrue() {
        Mockito.when(orderRepository.findById(DUMMY_ID)).thenReturn(Optional.ofNullable(orderRequest));
        Assertions.assertEquals("Booking deleted", orderService.deleteOrderById(DUMMY_ID));
        Mockito.verify(orderRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(orderRepository, Mockito.times(1)).delete(any());
    }

    @Test
    public void getOrders_ReturnsTrue() {
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(orderRequest));
        Assertions.assertEquals(List.of(orderRequest), orderService.getOrders());
        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
    }
}

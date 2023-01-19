package com.stackroute.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stackroute.paymentservice.config.Producer;
import com.stackroute.paymentservice.model.OrderRequest;
import com.stackroute.paymentservice.payloads.PaymentResponseDTO;
import com.stackroute.paymentservice.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTests {

    OrderRequest orderRequest;
    OrderRequest orderRequest1;
    @InjectMocks
    PaymentController paymentController;
    @Mock
    RazorpayClient client;
    @Mock
    OrderService orderService;
    @Autowired
    MockMvc mockMvc;
    @Mock
    private Producer producer;

    private int DUMMY_ID = 1;
    private String DUMMY_NAME = "DUMMY_NAME";
    private String DUMMY_EMAIL = "DUMMYMAIL@MAIL.COM";
    private String DUMMY_NUMBER = "1234567890";
    private Double DUMMY_AMOUNT = 1000.00;
    private Double DUMMY_AMOUNT_ZERO = 0.0;
    private static final String SECRET_ID1 = "rzp_test_81cLJdyBoC23FP";
    private static final String SECRET_KEY1 = "UJtZQxQYdqwT3FhyWdpLUMQo";

    @BeforeEach
    public void setUp() throws RazorpayException {
        orderRequest = new OrderRequest(DUMMY_ID, DUMMY_NAME, DUMMY_EMAIL, DUMMY_NUMBER, DUMMY_AMOUNT);
        orderRequest1 = new OrderRequest(DUMMY_ID, DUMMY_NAME, DUMMY_EMAIL, DUMMY_NUMBER, DUMMY_AMOUNT_ZERO);
        client = new RazorpayClient(SECRET_ID1, SECRET_KEY1);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @AfterEach
    public void tearDown() {
        orderRequest = null;
        orderRequest1 = null;
    }

        @Test
    public void getHomeInitTest_RazorpayOrderIdNotNull() throws Exception {
        when(orderService.getOrders()).thenReturn(List.of(orderRequest));
        mockMvc.perform(post("/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(orderRequest)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getHomeInitTest_AmountLessThanZero() throws Exception {
        when(orderService.getOrders()).thenReturn(List.of(orderRequest1));
        mockMvc.perform(post("/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(orderRequest)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getHomeInitTest_OrdersNull() throws Exception {
        when(orderService.getOrders()).thenReturn(null);
        mockMvc.perform(post("/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(orderRequest)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void paymentSuccess_Success() throws Exception {
        PaymentResponseDTO paymentDTO = new PaymentResponseDTO();
        paymentDTO.setBookingId(1);
        paymentDTO.setPaymentId("adasdsa");
        paymentDTO.setPaymentSignature("adsasfcasca");
        paymentDTO.setOrderId("fhvhbhbj");
        doNothing().when(producer).sendMessageToRabbitMq(paymentDTO);
        mockMvc.perform(post("/payment/success")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("razorpay_payment_id", paymentDTO.getPaymentId())
                        .param("razorpay_order_id", paymentDTO.getOrderId())
                        .param("razorpay_signature", paymentDTO.getPaymentSignature())
                        .param("razorpay_customer_id", String.valueOf(paymentDTO.getBookingId())))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    private static String jsonToString(final Object o) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(o);
            result = jsonContent;
            return result;

        } catch (JsonProcessingException e) {
            result = "JsonProcessingException";
        }
        return result;
    }

}

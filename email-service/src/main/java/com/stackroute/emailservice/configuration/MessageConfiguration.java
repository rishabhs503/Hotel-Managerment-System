package com.stackroute.emailservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MessageConfiguration {

    private static final String EXCHANGE_NAME = "ease_my_stay_exchange";
    private static final String QUEUE_TO_GET_EMAILER = "queue_to_get_emailer";
    private static final String QUEUE_TO_GET_EMAILER_SUCCESS = "queue_to_get_emailer_success";
    private static final String QUEUE_TO_GET_EMAILER_RESET = "queue_to_get_emailer_reset";
    private static final String QUEUE_TO_GET_EMAILER_RESET_SUCCESS = "queue_to_get_emailer_reset_success";
    private static final String QUEUE_TO_GET_BOOKING = "queue_to_get_booking";
    private static final String QUEUE_TO_GET_CANCELLATION = "queue_to_get_cancellation";
    private static final String QUEUE_TO_GET_NEW_HOTEL ="queue_to_get_new_hotel";
    private static final String QUEUE_TO_GET_UPDATE_HOTEL ="queue_to_get_update_hotel";
    private static final String QUEUE_TO_GET_DELETE_HOTEL ="queue_to_get_delete_hotel";

    // Routing Key's-----------------------------------------------------------------------------------------------------------

    private final static String ROUTING_KEY_FOR_EMAIL = "user_routing_to_email";
    private final static String ROUTING_KEY_FOR_EMAIL_SUCCESS = "user_routing_to_email_success";
    private final static String ROUTING_KEY_FOR_EMAIL_RESET = "user_routing_to_email_reset";
    private final static String ROUTING_KEY_FOR_EMAIL_RESET_SUCCESS = "user_routing_to_email_reset_success";
    private final static String ROUTING_KEY_FOR_BOOKING = "booking_routing_to_booking";
    private final static String ROUTING_KEY_FOR_CANCELLATION = "booking_routing_to_cancellation";
    private final static String ROUTING_KEY_FOR_NEW_HOTEL = "property_routing_to_new_hotel_mail";
    private final static String ROUTING_KEY_FOR_UPDATE_HOTEL = "property_routing_to_update_hotel_mail";
    private final static String ROUTING_KEY_FOR_DELETE_HOTEL = "property_routing_to_delete_hotel_mail";
    // Beans----------------------------------------------------------------------------------------------------------
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_TO_GET_EMAILER, false);
    }

    @Bean
    public Queue emailWelQueue() {
        return new Queue(QUEUE_TO_GET_EMAILER_SUCCESS, false);
    }

    @Bean
    public Queue emailResetQueue() {
        return new Queue(QUEUE_TO_GET_EMAILER_RESET, false);
    }

    @Bean
    public Queue emailResetSuccessQueue() {
        return new Queue(QUEUE_TO_GET_EMAILER_RESET_SUCCESS, false);
    }

    @Bean
    public Queue bookingQueue() {return new Queue(QUEUE_TO_GET_BOOKING, false); }

    @Bean
    public Queue cancellationQueue() { return new Queue(QUEUE_TO_GET_CANCELLATION, false); }

    @Bean
    @Primary
    public Queue newHotelQueue() {return new Queue(QUEUE_TO_GET_NEW_HOTEL, false); }

    @Bean
    public Queue updateHotelQueue() {return new Queue(QUEUE_TO_GET_UPDATE_HOTEL, false); }

    @Bean
    public Queue deleteHotelQueue() {return new Queue(QUEUE_TO_GET_DELETE_HOTEL, false); }


    //Bindings--------------------------------------------------------------------------------------------------------

    @Bean
    Binding bindingToGetEmail(Queue emailQueue, DirectExchange exchange) {
        return BindingBuilder.bind(emailQueue()).to(exchange).with(ROUTING_KEY_FOR_EMAIL);
    }

    @Bean
    Binding bindingToGetWelEmail(Queue emailQueue, DirectExchange exchange) {
        return BindingBuilder.bind(emailWelQueue()).to(exchange).with(ROUTING_KEY_FOR_EMAIL_SUCCESS);
    }

    @Bean
    Binding bindingToGetResetEmail(Queue emailQueue, DirectExchange exchange) {
        return BindingBuilder.bind(emailResetQueue()).to(exchange).with(ROUTING_KEY_FOR_EMAIL_RESET);
    }

    @Bean
    Binding bindingToGetResetSuccessEmail(Queue emailQueue, DirectExchange exchange) {
        return BindingBuilder.bind(emailResetSuccessQueue()).to(exchange).with(ROUTING_KEY_FOR_EMAIL_RESET_SUCCESS);
    }

    @Bean
    Binding bindingToBookingMail(Queue bookingQueue, DirectExchange exchange) {
        return BindingBuilder.bind(bookingQueue()).to(exchange).with(ROUTING_KEY_FOR_BOOKING);
    }

    @Bean
    Binding bindingToCancellationEmail(Queue bookingQueue, DirectExchange exchange) {
        return BindingBuilder.bind(cancellationQueue()).to(exchange).with(ROUTING_KEY_FOR_CANCELLATION);
    }

    @Bean
    Binding bindingToNewHotelEmail(Queue hotelPropertyQueue, DirectExchange exchange) {
        return BindingBuilder.bind(newHotelQueue()).to(exchange).with(ROUTING_KEY_FOR_NEW_HOTEL);
    }

    @Bean
    Binding bindingToUpdateHotelEmail(Queue hotelPropertyQueue, DirectExchange exchange) {
        return BindingBuilder.bind(updateHotelQueue()).to(exchange).with(ROUTING_KEY_FOR_UPDATE_HOTEL);
    }

    @Bean
    Binding bindingToDeleteHotelEmail(Queue hotelPropertyQueue, DirectExchange exchange) {
        return BindingBuilder.bind(deleteHotelQueue()).to(exchange).with(ROUTING_KEY_FOR_DELETE_HOTEL);
    }
}

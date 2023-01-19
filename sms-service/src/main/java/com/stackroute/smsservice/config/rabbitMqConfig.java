package com.stackroute.smsservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class rabbitMqConfig {

    @Value("${rabbitmq.exchange}")
    private String exchange_name;

    @Value("${queue.name.userVerification}")
    private String queueNameforUserVerification;
    @Value("${rabbitmq.routingkey.userVerification}")
    private String routingkeyforUserVerification;


    @Value("${queue.name.userForgetPassword}")
    private String queueNameforUserForgetPassword;
    @Value("${rabbitmq.routingkey.userForgetPassword}")
    private String routingkeyforUserForgetPassword;

    @Value("${queue.name.hotelPropertyRegistration}")
    private String queueNameforHotelPropertyRegistration;
    @Value("${rabbitmq.routingkey.hotelPropertyRegistration}")
    private String routingkeyforHotelPropertyRegistration;

    @Value("${queue.name.userHotelBooking}")
    private String queueNameforUserHotelBooking;
    @Value("${rabbitmq.routingkey.userHotelBooking}")
    private String routingkeyforUserHotelBooking;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange_name);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Primary
    public Queue VerificationWithOtpQueue() {
        return new Queue(queueNameforUserVerification, false);
    }

    @Bean
    public Queue ForgetPasswordQueue() {
        return new Queue(queueNameforUserForgetPassword, false);
    }

    @Bean
    public Queue HotelBookingQueue() {
        return new Queue(queueNameforUserHotelBooking, false);
    }

    @Bean
    public Queue HotelPropertyRegistrationQueue() {
        return new Queue(queueNameforHotelPropertyRegistration, false);
    }


    @Bean
    Binding bindingToOtpVerification(Queue queueNameforUserVerification, DirectExchange exchange) {
        return BindingBuilder.bind(VerificationWithOtpQueue()).to(exchange).with(routingkeyforUserVerification);
    }

    @Bean
    Binding bindingToForgetPassword(Queue queueNameforUserForgetPassword, DirectExchange exchange) {
        return BindingBuilder.bind(ForgetPasswordQueue()).to(exchange).with(routingkeyforUserForgetPassword);
    }

    @Bean
    Binding bindingToHotelBooking(Queue queueNameforUserHotelBooking, DirectExchange exchange) {
        return BindingBuilder.bind(HotelBookingQueue()).to(exchange).with(routingkeyforUserHotelBooking);
    }

    @Bean
    Binding bindingToHotelPropertyRegistration(Queue queueNameforHotelPropertyRegistration, DirectExchange exchange) {
        return BindingBuilder.bind(HotelPropertyRegistrationQueue()).to(exchange).with(routingkeyforHotelPropertyRegistration);
    }

}

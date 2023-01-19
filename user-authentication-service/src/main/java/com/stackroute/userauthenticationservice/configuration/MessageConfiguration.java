package com.stackroute.userauthenticationservice.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class acts as configuration class for RabbitMq communication
 */
@Configuration
public class MessageConfiguration {

    private static final String EXCHANGE_NAME = "ease_my_stay_exchange";
    private static final String QUEUE_TO_SAVE_USER = "queue_to_save_user";
    private static final String QUEUE_TO_UPDATE_USER = "queue_to_update_user";
    private static final String QUEUE_TO_DELETE_USER = "queue_to_delete_user";
    private static final String ROUTING_KEY_FOR_SAVE_USER = "user_routing_to_save_user";
    private static final String ROUTING_KEY_FOR_UPDATE_USER = "user_routing_to_update_user";
    private static final String ROUTING_KEY_FOR_DELETE_USER = "user_routing_to_delete_user";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue addUserQueue() {
        return new Queue(QUEUE_TO_SAVE_USER, false);
    }

    @Bean
    public Queue updateUserQueue() {
        return new Queue(QUEUE_TO_UPDATE_USER, false);
    }

    @Bean
    public Queue deleteUserQueue() {
        return new Queue(QUEUE_TO_DELETE_USER, false);
    }

    @Bean
    Binding bindingToSaveUser(Queue addUserQueue, DirectExchange exchange) {
        return BindingBuilder.bind(addUserQueue()).to(exchange).with(ROUTING_KEY_FOR_SAVE_USER);
    }

    @Bean
    Binding bindingToUpdateUser(Queue updateUserQueue, DirectExchange exchange) {
        return BindingBuilder.bind(updateUserQueue()).to(exchange).with(ROUTING_KEY_FOR_UPDATE_USER);
    }

    @Bean
    Binding bindingToDeleteUser(Queue deleteUserQueue, DirectExchange exchange) {
        return BindingBuilder.bind(deleteUserQueue()).to(exchange).with(ROUTING_KEY_FOR_DELETE_USER);
    }

}


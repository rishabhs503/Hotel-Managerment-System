package com.stackroute.hotelbookingservice.configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MessageConfiguration {

	    private String paymentQueue="payment_details_queue";
		public static final String exchangeName="ease_my_stay_exchange";
        
	    @Bean
	    public DirectExchange directExchange() {
	        return new DirectExchange(exchangeName);
	    }

	    @Bean
	    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	        return new Jackson2JsonMessageConverter();
	    }

	    @Bean
	    public Queue paymentQueue()
	    {
	        return new Queue(paymentQueue,false);
	    }

	    @Bean
	    Binding bindingOrderDetails(Queue paymentQueue, DirectExchange directExchange)
	    {
		  return BindingBuilder.bind(paymentQueue()).to(directExchange).with("payment_response_routing_key");
	    }

	    @Bean
	    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
	        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
	        return rabbitTemplate;
	    }
     
}

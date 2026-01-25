package com.example.producer.configurations;
import com.example.producer.listeners.RabbitConnectionListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "demo.queue";
    public static final String EXCHANGE = "demo.exchange";
    public static final String ROUTING_KEY = "demo.key";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitConnectionListener connectionListener;

    @PostConstruct
    public void init() {
        if (connectionFactory instanceof CachingConnectionFactory) {
            ((CachingConnectionFactory) connectionFactory).addConnectionListener(connectionListener);
        }
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
}

package com.example.consumer.services;

import com.example.consumer.configurations.RabbitMQConfig;
import com.example.consumer.models.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMessage(OrderMessage orderMessage) {
        logger.info("Received message: {}", orderMessage);
        logger.info("Order ID: {}", orderMessage.getOrderId());
        logger.info("Message: {}", orderMessage.getMessage());
    }
}

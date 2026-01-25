package com.example.consumer.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConnectionListener implements ConnectionListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConnectionListener.class);

    @Override
    public void onCreate(Connection connection) {
        logger.info("✅ RabbitMQ connection established successfully!");
        logger.info("Connection details: {}", connection);
    }

    @Override
    public void onClose(Connection connection) {
        logger.warn("⚠️ RabbitMQ connection closed");
    }

    @Override
    public void onShutDown(com.rabbitmq.client.ShutdownSignalException signal) {
        logger.error("❌ RabbitMQ connection shutdown: {}", signal.getMessage());
    }
}

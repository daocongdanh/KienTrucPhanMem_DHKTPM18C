package com.example.producer.models;

public class OrderMessage {
    private String message;
    private String orderId;

    public OrderMessage() {
    }

    public OrderMessage(String message, String orderId) {
        this.message = message;
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "message='" + message + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}

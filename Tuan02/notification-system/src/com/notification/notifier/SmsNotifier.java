package com.notification.notifier;

public class SmsNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[SMS] Sending: " + message);
    }
}

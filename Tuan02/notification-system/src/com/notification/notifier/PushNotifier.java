package com.notification.notifier;

public class PushNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[PUSH] Sending: " + message);
    }
}

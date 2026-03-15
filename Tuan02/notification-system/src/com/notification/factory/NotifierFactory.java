package com.notification.factory;

import com.notification.notifier.EmailNotifier;
import com.notification.notifier.Notifier;
import com.notification.notifier.PushNotifier;
import com.notification.notifier.SmsNotifier;

public class NotifierFactory {
    public static Notifier createNotifier(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        switch (type.trim().toUpperCase()) {
            case "EMAIL":
                return new EmailNotifier();
            case "SMS":
                return new SmsNotifier();
            case "PUSH":
                return new PushNotifier();
            default:
                throw new IllegalArgumentException("Unsupported notifier type: " + type);
        }
    }
}

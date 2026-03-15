package com.notification.main;

import com.notification.factory.NotifierFactory;
import com.notification.notifier.Notifier;
import com.notification.singleton.NotificationLogger;

public class Main {
    public static void main(String[] args) {
        NotificationLogger logger = NotificationLogger.getInstance();

        processNotification("EMAIL", "Don hang #DH001 da duoc xac nhan", logger);
        processNotification("SMS", "Don hang #DH002 dang duoc giao", logger);
        processNotification("PUSH", "Khuyen mai 30% cho nguoi dung moi", logger);

        System.out.println("\n=== Lich su gui thong bao ===");
        for (String log : logger.getAllLogs()) {
            System.out.println(log);
        }
    }

    private static void processNotification(String type, String message, NotificationLogger logger) {
        try {
            Notifier notifier = NotifierFactory.createNotifier(type);
            notifier.send(message);
            logger.log(type, message, "SUCCESS");
        } catch (IllegalArgumentException ex) {
            logger.log(type, message, "FAILED: " + ex.getMessage());
        }
    }
}

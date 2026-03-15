package com.notification.singleton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationLogger {
    private static NotificationLogger instance;
    private final List<String> logs;

    private NotificationLogger() {
        this.logs = new ArrayList<>();
    }

    public static synchronized NotificationLogger getInstance() {
        if (instance == null) {
            instance = new NotificationLogger();
        }
        return instance;
    }

    public void log(String channel, String message, String status) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entry = String.format("[%s] Channel=%s | Message=%s | Status=%s", timestamp, channel, message, status);
        logs.add(entry);
        System.out.println("[LOG] " + entry);
    }

    public List<String> getAllLogs() {
        return Collections.unmodifiableList(logs);
    }
}

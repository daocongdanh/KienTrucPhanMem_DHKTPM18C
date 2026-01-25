package com.example.producer.controllers;

import com.example.producer.models.OrderMessage;
import com.example.producer.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody OrderMessage orderMessage) {
        messageService.sendMessage(orderMessage);
        return ResponseEntity.ok("Message sent successfully: " + orderMessage);
    }
}

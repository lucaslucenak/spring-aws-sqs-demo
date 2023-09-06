package com.lucaslucenak.springawssqs.controllers;

import com.lucaslucenak.springawssqs.services.SqsService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SqsController {

    @Autowired
    SqsService sqsService;

    @GetMapping(value = "/receive-message/{maxNumberOfMessages}")
    public ResponseEntity<String> receiveMessage(@PathParam("maxNumberOfMessages") Integer maxNumberOfMessages) {
        String messages = sqsService.receiveMessages(maxNumberOfMessages);
        System.out.println(messages);
        return ResponseEntity.ok().body(messages);
    }

    @PostMapping(value = "/send-message")
    public void sendMessage(@RequestBody String message) {
        sqsService.sendMessage(message);
    }

}

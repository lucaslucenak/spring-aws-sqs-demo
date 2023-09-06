package com.lucaslucenak.springawssqs.controllers;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.lucaslucenak.springawssqs.entities.Address;
import com.lucaslucenak.springawssqs.services.SqsService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SqsController {

    @Autowired
    SqsService sqsService;

    @GetMapping(value = "/receive-message/{maxNumberOfMessages}")
    public ResponseEntity<List<String>> receiveMessage(@PathVariable Integer maxNumberOfMessages) {
        List<String> messages = sqsService.receiveMessages(maxNumberOfMessages);
        System.out.println(messages);
        System.out.println(maxNumberOfMessages);
        return ResponseEntity.ok().body(messages);
    }

    @PostMapping(value = "/send-message")
    public ResponseEntity<SendMessageResult> sendMessage(@RequestBody String message) {
        return ResponseEntity.ok().body(sqsService.sendMessage(message));
    }

    @PostMapping(value = "/send-address")
    public ResponseEntity<SendMessageResult> sendAddress(@RequestBody Address address) {
        return ResponseEntity.ok().body(sqsService.sendMessage(address));
    }

}

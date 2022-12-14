package com.meetapp.meetapp.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.meetapp.meetapp.model.Message;
import com.meetapp.meetapp.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages/{chatroomId}")
    public List<Message> retrieveChatroomMessages(@PathVariable Integer chatroomId, @RequestParam Integer page,
                                                  HttpSession session) {
        return messageService.retrieveMessages(chatroomId, session, page);
    }

    @PostMapping("/messages/{chatroomId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Message createMessage(HttpSession session, @PathVariable Integer chatroomId, @RequestBody TextNode content) {
        return messageService.createMessage(chatroomId, session, content.asText());
    }
}

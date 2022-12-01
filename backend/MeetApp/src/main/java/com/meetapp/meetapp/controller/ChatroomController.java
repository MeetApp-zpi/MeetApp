package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.model.Chatroom;
import com.meetapp.meetapp.service.ChatroomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatroomController {
    private final ChatroomService chatroomService;

    public ChatroomController(ChatroomService chatroomService) {
        this.chatroomService = chatroomService;
    }

    @GetMapping("/chatrooms/exists/{anotherClientEmail}")
    public Boolean existsChatroomBetweenClients(HttpSession session, @PathVariable String anotherClientEmail) {
        return chatroomService.existsChatroomBetweenClients(session, anotherClientEmail);
    }

    @GetMapping("/chatrooms/forClient")
    public List<Chatroom> retrieveChatroomsForLoggedInClient(HttpSession session) {
        return chatroomService.retrieveChatroomsForLoggedInClient(session);
    }

    @PostMapping("/chatrooms/{anotherClientEmail}")
    public Chatroom createChatroom(HttpSession session, @PathVariable String anotherClientEmail) {
        return chatroomService.createChatroom(session, anotherClientEmail);
    }
}

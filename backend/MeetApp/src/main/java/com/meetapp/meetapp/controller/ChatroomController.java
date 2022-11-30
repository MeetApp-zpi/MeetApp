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

    @GetMapping("/chatrooms/exists/{firstClientEmail}/{secondClientEmail}")
    public Boolean existsChatroomBetweenClients(@PathVariable String firstClientEmail,
                                                @PathVariable String secondClientEmail) {
        return chatroomService.existsChatroomBetweenClients(firstClientEmail, secondClientEmail);
    }

    @GetMapping("/chatrooms/forClient")
    public List<Chatroom> retrieveChatroomsForLoggedInClient(HttpSession session) {
        return chatroomService.retrieveChatroomsForLoggedInClient(session);
    }

    @PostMapping("/chatrooms/{firstClientEmail}/{secondClientEmail}")
    public Chatroom createChatroom(@PathVariable String firstClientEmail, @PathVariable String secondClientEmail) {
        return chatroomService.createChatroom(firstClientEmail, secondClientEmail);
    }
}

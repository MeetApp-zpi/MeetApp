package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.ChatroomDTO;
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

    @GetMapping("/chatrooms/existsById/{chatroomId}")
    public Boolean existsChatroom(HttpSession session, @PathVariable Integer chatroomId) {
        return chatroomService.existsChatroom(session, chatroomId);
    }

    @GetMapping("/chatrooms/forClient")
    public List<ChatroomDTO> retrieveChatroomsForLoggedInClient(HttpSession session) {
        return chatroomService.retrieveChatroomsForLoggedInClient(session);
    }

    @GetMapping("/chatrooms/markAsUnread/{chatroomId}")
    public void markChatroomAsUnread(HttpSession session, @PathVariable Integer chatroomId) {
        chatroomService.markChatroomAsUnread(session, chatroomId);
    }

    @GetMapping("/chatrooms/markAsRead/{chatroomId}")
    public void markChatroomAsRead(HttpSession session, @PathVariable Integer chatroomId) {
        chatroomService.markChatroomAsRead(session, chatroomId);
    }

    @GetMapping("/chatrooms/haveUnreadMessage")
    public Boolean haveUnreadMessage(HttpSession session) {
        return chatroomService.haveUnreadMessage(session);
    }

    @PostMapping("/chatrooms/{anotherClientEmail}")
    public Chatroom createChatroom(HttpSession session, @PathVariable String anotherClientEmail) {
        return chatroomService.createChatroom(session, anotherClientEmail);
    }
}

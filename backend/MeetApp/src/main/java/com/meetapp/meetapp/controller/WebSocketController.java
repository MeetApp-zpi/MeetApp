package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/message/{chatroomId}")
    @SendToUser("/queue/reply")
    public String connectToChatroom(@DestinationVariable String chatroomId, HttpSession session) {
        return SessionManager.retrieveEmailOrThrow(session);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}

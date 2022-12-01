package com.meetapp.meetapp.dto;

import com.meetapp.meetapp.model.Client;
import lombok.Data;

@Data
public class ChatroomDTO {
    private Integer id;
    private Client chatPartner;
    private Boolean hasUnreadMessage;

    public ChatroomDTO(Integer id, Client chatPartner, Boolean hasUnreadMessage) {
        this.id = id;
        this.chatPartner = chatPartner;
        this.hasUnreadMessage = hasUnreadMessage;
    }
}

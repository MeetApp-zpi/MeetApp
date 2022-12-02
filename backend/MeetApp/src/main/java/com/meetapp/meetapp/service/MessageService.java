package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Chatroom;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Message;
import com.meetapp.meetapp.repository.ChatroomRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.MessageRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MessageService {

    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final ClientRepository clientRepository;

    public MessageService(MessageRepository messageRepository, ChatroomRepository chatroomRepository,
                          ClientRepository clientRepository) {
        this.messageRepository = messageRepository;
        this.chatroomRepository = chatroomRepository;
        this.clientRepository = clientRepository;
    }

    public List<Message> retrieveMessages(Integer chatroomId, HttpSession session, Integer page) {
        Chatroom foundChatroom = findChatroomOrThrow(chatroomId);
        Client foundChatter = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        if (foundChatter.equals(foundChatroom.getSecondClient()) || foundChatter.equals(foundChatroom.getFirstClient())) {
            PageRequest nextPage = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "creationDate"));

            return messageRepository.findAll(nextPage).stream().toList();
        } else {
            throw new IllegalArgumentException("A client with id: " + foundChatter.getId() + " is not a part of" +
                    " chatroom with id: " + foundChatroom.getId());
        }
    }

    public Message createMessage(Integer chatroomId, HttpSession session, String content) {
        Chatroom foundChatroom = findChatroomOrThrow(chatroomId);
        Client foundAuthor = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        if (foundAuthor.equals(foundChatroom.getFirstClient()) || foundAuthor.equals(foundChatroom.getSecondClient())) {
            return messageRepository.save(new Message(foundChatroom, foundAuthor, content));
        } else {
            throw new IllegalArgumentException("A client with id: " + foundAuthor.getId() + " is not a part of" +
                    " chatroom with id: " + foundChatroom.getId());
        }
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist."));
    }

    public Chatroom findChatroomOrThrow(Integer chatroomId) {
        return chatroomRepository.findById(chatroomId).orElseThrow(
                () -> new NoSuchElementException("A chatroom with id: " + chatroomId + " does not exist."));
    }

    public Message findMessageOrThrow(Integer messageId) {
        return messageRepository.findById(messageId).orElseThrow(
                () -> new NoSuchElementException("A message with id: " + messageId + " does not exist."));
    }
}

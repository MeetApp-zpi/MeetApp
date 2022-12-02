package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.ChatroomDTO;
import com.meetapp.meetapp.model.Chatroom;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.repository.ChatroomRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final ClientRepository clientRepository;

    public ChatroomService(ChatroomRepository chatroomRepository, ClientRepository clientRepository) {
        this.chatroomRepository = chatroomRepository;
        this.clientRepository = clientRepository;
    }

    public Boolean existsChatroomBetweenClients(HttpSession session, String anotherClientEmail) {
        Client firstClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        Client secondClient = findClientOrThrow(anotherClientEmail);

        return chatroomRepository.existsChatroomByFirstClientAndSecondClient(firstClient, secondClient) ||
                chatroomRepository.existsChatroomByFirstClientAndSecondClient(secondClient, firstClient);
    }

    public Boolean existsChatroom(HttpSession session, Integer chatroomId) {
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        Chatroom foundChatroom = findChatroomOrThrow(chatroomId);

        return foundClient.equals(foundChatroom.getFirstClient()) || foundClient.equals(foundChatroom.getSecondClient());
    }

    public List<ChatroomDTO> retrieveChatroomsForLoggedInClient(HttpSession session) {
        Client client = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        return chatroomRepository.findAllByFirstClientOrSecondClient(client, client)
                .stream().map((Chatroom cr) -> new ChatroomDTO(cr.getId(), getPartner(client, cr),
                        hasUnreadMessageInChatroom(client, cr))).toList();
    }

    public Chatroom createChatroom(HttpSession session, String anotherClientEmail) {
        Client firstClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        Client secondClient = findClientOrThrow(anotherClientEmail);

        if (!existsChatroomBetweenClients(session, anotherClientEmail)) {
            return chatroomRepository.save(new Chatroom(firstClient, secondClient));
        } else {
            throw new IllegalArgumentException("A chatroom between " + firstClient.getEmail() + " and " + anotherClientEmail +
                    " already exists");
        }
    }

    public void markChatroomAsUnread(HttpSession session, Integer chatroomId) {
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        Chatroom foundChatroom = findChatroomOrThrow(chatroomId);

        if (foundChatroom.getFirstClient().equals(foundClient)) {
            foundChatroom.setHasSecondClientRead(false);
            chatroomRepository.save(foundChatroom);
        } else if (foundChatroom.getSecondClient().equals(foundClient)) {
            foundChatroom.setHasFirstClientRead(false);
            chatroomRepository.save(foundChatroom);
        }
    }

    public void markChatroomAsRead(HttpSession session, Integer chatroomId) {
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        Chatroom foundChatroom = findChatroomOrThrow(chatroomId);

        if (foundChatroom.getFirstClient().equals(foundClient)) {
            foundChatroom.setHasSecondClientRead(true);
            chatroomRepository.save(foundChatroom);
        } else if (foundChatroom.getSecondClient().equals(foundClient)) {
            foundChatroom.setHasFirstClientRead(true);
            chatroomRepository.save(foundChatroom);
        }
    }

    public Boolean haveUnreadMessage(HttpSession session) {
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        return chatroomRepository.existsChatroomByFirstClientAndHasFirstClientRead(foundClient, false)
                || chatroomRepository.existsChatroomBySecondClientAndHasSecondClientRead(foundClient, false);
    }

    public Chatroom findChatroomOrThrow(Integer chatroomId) {
        return chatroomRepository.findById(chatroomId).orElseThrow(
                () -> new NoSuchElementException("A chatroom with id: " + chatroomId + " does not exist"));
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist"));
    }

    public Client getPartner(Client currentClient, Chatroom chatroom) {
        if (chatroom.getFirstClient().equals(currentClient)) {
            return chatroom.getSecondClient();
        } else {
            return chatroom.getFirstClient();
        }
    }

    public Boolean hasUnreadMessageInChatroom(Client currentClient, Chatroom chatroom) {
        if (chatroom.getFirstClient().equals(currentClient)) {
            return chatroom.getHasFirstClientRead();
        } else {
            return chatroom.getHasSecondClientRead();
        }
    }
}

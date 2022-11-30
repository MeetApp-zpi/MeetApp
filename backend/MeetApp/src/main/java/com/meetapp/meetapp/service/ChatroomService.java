package com.meetapp.meetapp.service;

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

    public Boolean existsChatroomBetweenClients(String firstClientEmail, String secondClientEmail) {
        Client firstClient = findClientOrThrow(firstClientEmail);
        Client secondClient = findClientOrThrow(secondClientEmail);

        return chatroomRepository.existsChatroomByFirstClientAndSecondClient(firstClient, secondClient) ||
                chatroomRepository.existsChatroomByFirstClientAndSecondClient(secondClient, firstClient);
    }

    public List<Chatroom> retrieveChatroomsForLoggedInClient(HttpSession session) {
        Client client = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        return chatroomRepository.findAllByFirstClientOrSecondClient(client, client);
    }

    public Chatroom createChatroom(String firstClientEmail, String secondClientEmail) {
        Client firstClient = findClientOrThrow(firstClientEmail);
        Client secondClient = findClientOrThrow(secondClientEmail);

        if (!existsChatroomBetweenClients(firstClientEmail, secondClientEmail)) {
            return chatroomRepository.save(new Chatroom(firstClient, secondClient));
        } else {
            throw new IllegalArgumentException("A chatroom between " + firstClientEmail + " and " + secondClientEmail +
                    " already exists");
        }
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist"));
    }
}

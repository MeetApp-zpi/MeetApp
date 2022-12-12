package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Chatroom;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.repository.ChatroomRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatroomServiceTest {

    @Test
    public void findClientThrow() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () ->
                chatroomService.findClientOrThrow("tearg@gmail.com"));
    }

    @Test
    public void findClientThrowId() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () ->
                chatroomService.findClientOrThrow(1));
    }

    @Test
    public void findChatroomThrow() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        when(chatroomRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () ->
                chatroomService.findChatroomOrThrow(1));
    }

    @Test
    public void existsChatroomBetweenClients() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.existsChatroomByFirstClientAndSecondClient(any(), any()))
                    .thenReturn(false);

            chatroomService.existsChatroomBetweenClients(session, "jdorka@gmail.com");
        }
    }

    @Test
    public void existsChatroom() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Client second = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findById(any())).thenReturn(Optional.of(new Chatroom(second, testClient)));

            chatroomService.existsChatroom(session, 1);
        }
    }

    @Test
    public void retrieveChatroomsForLoggedInClient() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findAllByFirstClientOrSecondClient(any(), any())).thenReturn(new ArrayList<>());

            chatroomService.retrieveChatroomsForLoggedInClient(session);
        }
    }

    @Test
    public void retrieveChatroomWithClient() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(clientRepository.findById(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findChatroomByFirstClientAndSecondClient(any(), any()))
                    .thenReturn(null);

            chatroomService.retrieveChatroomWithClient(session, 1);
        }
    }

    @Test
    public void retrieveChatroomWithClientSecond() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(clientRepository.findById(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findChatroomByFirstClientAndSecondClient(any(), any()))
                    .thenReturn(new Chatroom());

            chatroomService.retrieveChatroomWithClient(session, 1);
        }
    }

    @Test
    public void retrieveOtherClient() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findById(any())).thenReturn(Optional.of(new Chatroom(testClient, testClient)));

            chatroomService.retrieveOtherClient(session, 1);
        }
    }

    @Test
    public void retrieveOtherClientSecond() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Client secondClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findById(any())).thenReturn(Optional.of(new Chatroom(secondClient, testClient)));

            chatroomService.retrieveOtherClient(session, 1);
        }
    }

    @Test
    public void retrieveOtherClientThrow() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Client secondClient = new Client();
            Client thirdClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findById(any())).thenReturn(Optional.of(new Chatroom(secondClient, thirdClient)));

            Assertions.assertThrows(IllegalArgumentException.class, () ->
                    chatroomService.retrieveOtherClient(session, 1));
        }
    }

    @Test
    public void createChatroom() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.existsChatroomByFirstClientAndSecondClient(any(), any())).thenReturn(false);
            when(chatroomRepository.save(any())).thenReturn(new Chatroom());

            chatroomService.createChatroom(session, "jdorka@gmail.com");
        }
    }

    @Test
    public void createChatroomThrow() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.existsChatroomByFirstClientAndSecondClient(any(), any())).thenReturn(true);
            when(chatroomRepository.save(any())).thenReturn(new Chatroom());

            Assertions.assertThrows(IllegalArgumentException.class, () ->
                    chatroomService.createChatroom(session, "jdorka@gmail.com"));
        }
    }

    @Test
    public void markChatroomAsUnread() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findById(any())).thenReturn(Optional.of(new Chatroom(testClient, testClient)));
            when(chatroomRepository.save(any())).thenReturn(new Chatroom());

            chatroomService.markChatroomAsUnread(session, 1);
        }
    }

    @Test
    public void markChatroomAsUnreadSecond() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Client secondClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findById(any())).thenReturn(Optional.of(new Chatroom(secondClient, testClient)));
            when(chatroomRepository.save(any())).thenReturn(new Chatroom());

            chatroomService.markChatroomAsUnread(session, 1);
        }
    }

    @Test
    public void markChatroomAsRead() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findById(any())).thenReturn(Optional.of(new Chatroom(testClient, testClient)));
            when(chatroomRepository.save(any())).thenReturn(new Chatroom());

            chatroomService.markChatroomAsRead(session, 1);
        }
    }

    @Test
    public void markChatroomAsReadSecond() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Client secondClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.findById(any())).thenReturn(Optional.of(new Chatroom(secondClient, testClient)));
            when(chatroomRepository.save(any())).thenReturn(new Chatroom());

            chatroomService.markChatroomAsRead(session, 1);
        }
    }

    @Test
    public void haveUnreadMessage() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(chatroomRepository.existsChatroomByFirstClientAndHasFirstClientRead(any(), any())).thenReturn(false);
            when(chatroomRepository.existsChatroomBySecondClientAndHasSecondClientRead(any(), any()))
                    .thenReturn(true);

            chatroomService.haveUnreadMessage(session);
        }
    }

    @Test
    public void getPartner() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Chatroom chatroom = new Chatroom(testClient, testClient);

            chatroomService.getPartner(testClient, chatroom);
        }
    }

    @Test
    public void getPartnerSecond() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Client secondClient = new Client();
            Chatroom chatroom = new Chatroom(secondClient, testClient);

            chatroomService.getPartner(testClient, chatroom);
        }
    }

    @Test
    public void hasUnreadMessageInChatroom() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Client secondClient = new Client();
            Chatroom chatroom = new Chatroom(secondClient, testClient);

            chatroomService.hasUnreadMessageInChatroom(testClient, chatroom);
        }
    }

    @Test
    public void hasUnreadMessageInChatroomSecond() {
        ChatroomRepository chatroomRepository = mock(ChatroomRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        ChatroomService chatroomService = new ChatroomService(chatroomRepository, clientRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client testClient = new Client();
            Chatroom chatroom = new Chatroom(testClient, testClient);

            chatroomService.hasUnreadMessageInChatroom(testClient, chatroom);
        }
    }
}

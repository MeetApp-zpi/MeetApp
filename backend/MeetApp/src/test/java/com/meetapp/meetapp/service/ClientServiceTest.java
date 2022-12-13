package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.CategoryListDTO;
import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.PostRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @Test
    public void findClient() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));

        clientService.findClientOrThrow("jdorka@gmail.com");
    }

    @Test
    public void findClientThrow() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () -> clientService.findClientOrThrow("essabyku"));
    }

    @Test
    public void findClientIdThrow() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () -> clientService.findClientOrThrow(1));
    }

    @Test
    public void findClientCategories() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        when(categoryRepository.findAllById(any())).thenReturn(new ArrayList<>());

        clientService.findClientCategories(new HashSet<>(1));
    }

    @Test
    public void findPost() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        when(postRepository.findById(any())).thenReturn(Optional.of(new Post()));

        clientService.findPostOrThrow(1);
    }

    @Test
    public void findPostThrow() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () ->
                clientService.findPostOrThrow(1));
    }

    @Test
    public void postToDTO() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Announcement ann = new Announcement(new Client(), new Location(), "tetatgaerg", "gerguiaerng i",
                new HashSet<>());
        Meeting meeting = new Meeting(new Client(), new Location(), "tetatgaerg", "gerguiaerng i",
                Instant.now(), new HashSet<>(), 2);
        Event event = new Event(new Client(), new Location(), "tetatgaerg", "gerguiaerng i",
                Instant.now(), Instant.now(), new HashSet<>(), 2);
        ClientService.postToDto(ann);
        ClientService.postToDto(meeting);
        ClientService.postToDto(event);
    }

    @Test
    public void retrieveClientDetails() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            clientService.retrieveClientDetails(session);
        }
    }

    @Test
    public void retrieveClientPosts() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));
            when(postRepository.findAllByAuthorEmailIsAndIsActiveIs(any(), any(), any())).thenReturn(new ArrayList<>());
            clientService.retrieveClientPosts(1, 1);
        }
    }

    @Test
    public void retrieveClientPostsSession() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));
            when(postRepository.findAllByAuthorEmailIsAndIsActiveIs(any(), any(), any())).thenReturn(new ArrayList<>());
            clientService.retrieveClientPosts(session, 1);
        }
    }

    @Test
    public void retrieveClientInactivePosts() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(postRepository.findAllByAuthorEmailIsAndIsActiveIs(any(), any(), any())).thenReturn(new ArrayList<>());
            clientService.retrieveClientInactivePosts(session, 1);
        }
    }

    @Test
    public void retrieveClientDetailsId() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));
            clientService.retrieveClientDetails(1);
        }
    }

    @Test
    public void retrieveLoggedInUserActivities() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Announcement ann = new Announcement(new Client(), new Location(), "tetatgaerg", "gerguiaerng i",
                new HashSet<>());
        Meeting meeting = new Meeting(new Client(), new Location(), "tetatgaerg", "gerguiaerng i",
                Instant.now(), new HashSet<>(), 2);
        Event event = new Event(new Client(), new Location(), "tetatgaerg", "gerguiaerng i",
                Instant.now(), Instant.now(), new HashSet<>(), 2);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(postRepository.findAllByEnrolleesContains(any(), any())).thenReturn(List.of(ann, meeting, event));
            clientService.retrieveLoggedInUserActivities(session, 1);
        }
    }

    @Test
    public void isLoggedUserAuthorOfPost() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Client client = new Client("jdorka@gmail.com", "Jan", "Test", "iuniunuinuinu");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(postRepository.findById(any())).thenReturn(Optional.of(new Post(client, new Location(), new HashSet<>())));
            clientService.isLoggedUserAuthorOfPost(session, 1);
        }
    }

    @Test
    public void getEnrolleesOfPost() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Client client = new Client("jdorka@gmail.com", "Jan", "Test", "iuniunuinuinu");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(postRepository.findById(any())).thenReturn(Optional.of(new Post(client, new Location(), new HashSet<>())));
            clientService.getEnrolleesOfPost(2, 1);
        }
    }

    @Test
    public void createClientAccount() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Client client = new Client("jdorka@gmail.com", "Jan", "Test", "iuniunuinuinu");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn(client.getEmail());
            sm.when(() -> SessionManager.retrieveGivenNameOrThrow(any())).thenReturn(client.getFirstName());
            sm.when(() -> SessionManager.retrieveFamilyNameOrThrow(any())).thenReturn(client.getLastName());
            sm.when(() -> SessionManager.retrievePictureOrThrow(any())).thenReturn(client.getProfilePicture());
            when(clientRepository.existsByEmail(any())).thenReturn(true);
            clientService.createClientAccount(session);
        }
    }

    @Test
    public void createClientAccountPass() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Client client = new Client("jdorka@gmail.com", "Jan", "Test", "iuniunuinuinu");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn(client.getEmail());
            sm.when(() -> SessionManager.retrieveGivenNameOrThrow(any())).thenReturn(client.getFirstName());
            sm.when(() -> SessionManager.retrieveFamilyNameOrThrow(any())).thenReturn(client.getLastName());
            sm.when(() -> SessionManager.retrievePictureOrThrow(any())).thenReturn(client.getProfilePicture());
            when(clientRepository.existsByEmail(any())).thenReturn(false);
            when(clientRepository.save(any())).thenReturn(new Client());
            clientService.createClientAccount(session);
        }
    }

    @Test
    public void deleteClientAccount() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Client client = new Client("jdorka@gmail.com", "Jan", "Test", "iuniunuinuinu");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn(client.getEmail());
            when(postRepository.saveAll(any())).thenReturn(new ArrayList<>());
            when(postRepository.findAllByEnrolleesContains(any())).thenReturn(new ArrayList<>());
            when(postRepository.findAllByAuthorEmailIsAndIsActiveIs(any(), any())).thenReturn(new ArrayList<>());
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            clientService.deleteClientAccount(session);
        }
    }

    @Test
    public void retrieveClientCategories() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Client client = new Client("jdorka@gmail.com", "Jan", "Test", "iuniunuinuinu");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn(client.getEmail());
            when(categoryRepository.findByClients_Email(any())).thenReturn(new ArrayList<>());
            clientService.retrieveClientCategories(session);
        }
    }

    @Test
    public void retrieveClientCategoriesId() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Client client = new Client("jdorka@gmail.com", "Jan", "Test", "iuniunuinuinu");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn(client.getEmail());
            when(categoryRepository.findByClients_Id(any())).thenReturn(new ArrayList<>());
            clientService.retrieveClientCategories(1);
        }
    }

    @Test
    public void updateClientCategories() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        PostRepository postRepository = mock(PostRepository.class);
        HttpSession session = mock(HttpSession.class);

        ClientService clientService = new ClientService(clientRepository, categoryRepository, postRepository);

        Client client = new Client("jdorka@gmail.com", "Jan", "Test", "iuniunuinuinu");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn(client.getEmail());
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(categoryRepository.findAllById(any())).thenReturn(new ArrayList<>());
            when(clientRepository.save(any())).thenReturn(client);
            clientService.updateClientCategories(session, new CategoryListDTO());
        }
    }
}

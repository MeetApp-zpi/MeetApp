package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.CategoryListDTO;
import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Post;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    public ClientService(ClientRepository clientRepository, CategoryRepository categoryRepository) {
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
    }

    public Client retrieveClientDetails(HttpSession session) {
        return findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
    }

    public List<Post> retrieveLoggedInUserActivities(HttpSession session) {

    }

    public Client createClientAccount(HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        String givenName = SessionManager.retrieveGivenNameOrThrow(session);
        String familyName = SessionManager.retrieveFamilyNameOrThrow(session);
        String pictureUrl = SessionManager.retrievePictureOrThrow(session);

        if (clientRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with this e-mail address already exists");
        }

        return clientRepository.save(new Client(email, givenName, familyName, pictureUrl));
    }

    public Client deleteClientAccount(Integer clientId, HttpSession session) {
        String authenticatedEmail = SessionManager.retrieveEmailOrThrow(session);
        Client foundClient = findClientOrThrow(clientId);

        if (Objects.equals(foundClient.getEmail(), authenticatedEmail)) {
            foundClient.setEvents(null);
            foundClient.setMeetings(null);
            foundClient.setInterests(null);
            foundClient.setFirstName("Removed");
            foundClient.setLastName("Removed");
            foundClient.setEmail("Removed");
            foundClient.setIsDeleted(true);
            return clientRepository.save(foundClient);
        } else {
            throw new SecurityException(
                    "A client with email: " + authenticatedEmail + " cannot delete user with id: " + clientId);
        }
    }

    public List<Category> retrieveClientCategories(HttpSession session) {
        return categoryRepository.findByClients_Email(SessionManager.retrieveEmailOrThrow(session));
    }

    public List<Category> updateClientCategories(HttpSession session, CategoryListDTO updatedCategoriesDTO) {
        Client currentClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        List<Category> updatedCategories = findClientCategories(updatedCategoriesDTO.getCategories());
        currentClient.setInterests(new HashSet<>(updatedCategories));
        clientRepository.save(currentClient);
        return updatedCategories;
    }

    public List<Category> findClientCategories(Set<Integer> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public Client findClientOrThrow(String clientEmail) {
        return clientRepository.findClientByEmail(clientEmail).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + clientEmail + " does not exist."));
    }

    public Client findClientOrThrow(Integer clientId) {
        return clientRepository.findById(clientId).orElseThrow(
                () -> new NoSuchElementException("A client with id: " + clientId + " does not exist."));
    }
}

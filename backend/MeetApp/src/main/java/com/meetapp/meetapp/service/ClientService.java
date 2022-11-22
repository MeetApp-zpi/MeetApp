package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.CategoryListDTO;
import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Post;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.PostRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    private final PostRepository postRepository;
    public ClientService(ClientRepository clientRepository, CategoryRepository categoryRepository,
                         PostRepository postRepository) {
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public Client retrieveClientDetails(HttpSession session) {
        return findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
    }

    public boolean isLoggedUserAuthorOfPost(HttpSession session, Integer postId) {
        String loggedUserEmail = SessionManager.retrieveEmailOrThrow(session);
        Post foundPost = findPostOrThrow(postId);
        return foundPost.getAuthor().getEmail().equals(loggedUserEmail);
    }

    public List<Client> getEnrolleesOfPost(Integer postId) {
        Post foundPost = findPostOrThrow(postId);
        return foundPost.getEnrollees().stream().toList();
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
            foundClient.setPosts(null);
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

    public Post findPostOrThrow(Integer postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("A post with id: " + postId + " does not exist."));
    }
}

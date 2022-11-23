package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.CategoryListDTO;
import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Post;
import com.meetapp.meetapp.service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
    public ResponseEntity<String> handleIllegalArg(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/users/details")
    public Client getUserDetails(HttpSession session) {
        return clientService.retrieveClientDetails(session);
    }

    @GetMapping("users/{clientId}/details")
    public Client getUserDetails(@PathVariable Integer clientId) {
        return clientService.retrieveClientDetails(clientId);
    }

    @GetMapping("/users/categories")
    public List<Category> getClientCategories(HttpSession session) {
        return clientService.retrieveClientCategories(session);
    }

    @GetMapping("/users/{clientId}/categories")
    public List<Category> getClientCategories(@PathVariable Integer clientId) {
        return clientService.retrieveClientCategories(clientId);
    }
    
    @GetMapping("/users/activities")
    public List<Record> getClientActivities(HttpSession session) {
        return clientService.retrieveLoggedInUserActivities(session);
    }
    
    @GetMapping("/users/isAuthor/{postId}")
    public boolean isLoggedUserAuthorOfPost(HttpSession session, @PathVariable Integer postId) {
        return clientService.isLoggedUserAuthorOfPost(session, postId);
    }

    @GetMapping("/enrollees/{postId}")
    public List<Client> getEnrolleesOfPost(@PathVariable Integer postId) {
        return clientService.getEnrolleesOfPost(postId);
    }

    @PutMapping("/users/categories")
    public List<Category> updateClientCategories(HttpSession session, @RequestBody CategoryListDTO updatedCategories) {
        return clientService.updateClientCategories(session, updatedCategories);
    }

    @PostMapping("/users")
    public Client createAccount(HttpSession session) {
        return clientService.createClientAccount(session);
    }

    @PostMapping("/logout")
    public void logoutClient(HttpSession session) {
        session.invalidate();
        return;
    }

    @DeleteMapping("/users/{user_id}")
    public Client deleteAccount(@PathVariable Integer user_id, HttpSession session) {
        return clientService.deleteClientAccount(user_id, session);
    }
}

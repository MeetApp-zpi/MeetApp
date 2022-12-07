package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.CategoryListDTO;
import com.meetapp.meetapp.dto.ClientDetailsDTO;
import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.service.ClientService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ClientDetailsDTO getUserDetails(HttpSession session) {
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

    @GetMapping("/users/{userId}/posts")
    public List<Record> getClientPosts(@PathVariable Integer userId, @RequestParam Integer page) {
        return clientService.retrieveClientPosts(userId, page);
    }

    @GetMapping("/users/posts")
    public List<Record> getClientPosts(HttpSession session, @RequestParam Integer page) {
        return clientService.retrieveClientPosts(session, page);
    }

    @GetMapping("/users/postsInactive")
    public List<Record> getClientInactivePosts(HttpSession session, @RequestParam Integer page) {
        return clientService.retrieveClientInactivePosts(session, page);
    }

    @GetMapping("/users/activities")
    public List<Record> getClientActivities(HttpSession session, @RequestParam Integer page) {
        return clientService.retrieveLoggedInUserActivities(session, page);
    }

    @GetMapping("/users/isAuthor/{postId}")
    public boolean isLoggedUserAuthorOfPost(HttpSession session, @PathVariable Integer postId) {
        return clientService.isLoggedUserAuthorOfPost(session, postId);
    }

    @GetMapping("/enrollees/{postId}")
    public List<Client> getEnrolleesOfPost(@PathVariable Integer postId, @RequestParam Integer page) {
        return clientService.getEnrolleesOfPost(postId, page);
    }

    @GetMapping("/users/createAccount")
    public void createUserAccount(HttpSession session, HttpServletResponse response) throws IOException {
        Boolean isNewAccount = clientService.createClientAccount(session);
        if (isNewAccount) {
            response.sendRedirect("http://localhost:5173/chooseCategories");
        } else {
            response.sendRedirect("http://localhost:5173");
        }
    }

    @PutMapping("/users/categories")
    public List<Category> updateClientCategories(HttpSession session, @RequestBody CategoryListDTO updatedCategories) {
        return clientService.updateClientCategories(session, updatedCategories);
    }

    @PostMapping("/logout")
    public void logoutClient(HttpSession session) {
        session.invalidate();
        return;
    }

    @DeleteMapping("/users")
    public Client deleteAccount(HttpSession session) {
        return clientService.deleteClientAccount(session);
    }
}

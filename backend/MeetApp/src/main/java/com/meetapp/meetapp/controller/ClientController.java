package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArg(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/users")
    public Client createAccount(@RequestBody Client newClient) {
        return clientService.createClientAccount(newClient);
    }

    @DeleteMapping("/users/{user_id}")
    public Client deleteAccount(@PathVariable Integer user_id) {
        return clientService.deleteClientAccount(user_id);
    }
}

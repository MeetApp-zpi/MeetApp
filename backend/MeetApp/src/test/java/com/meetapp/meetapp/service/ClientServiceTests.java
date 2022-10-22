package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;

public class ClientServiceTests {

    @MockBean
    private ClientRepository clientRepo;

    private ClientService clientService;

    @BeforeEach
    public void setup() {
        this.clientRepo = Mockito.mock(ClientRepository.class);
        this.clientService = new ClientService(this.clientRepo);
    }

    @Test
    @DisplayName("Should throw when deleting non-existent user")
    public void deleteNonExistentUser() {
        Mockito.when(this.clientRepo.findClientById(0)).thenThrow(NoSuchElementException.class);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.clientService.deleteClientAccount(0);
        });
    }

    @Test
    @DisplayName("Successfully delete user")
    public void deleteUser() {
        Client toDelete = new Client("a@gmail.com", "Jan", "Test");
        Mockito.when(this.clientRepo.findClientById(1)).thenReturn(toDelete);
        Mockito.when(this.clientRepo.save(toDelete)).thenReturn(toDelete);

        Client expectedClient = new Client("a@gmail.com", "Jan", "Test");
        expectedClient.setIsDeleted(true);

        Assertions.assertEquals(expectedClient, this.clientService.deleteClientAccount(1));
    }

    @Test
    @DisplayName("Should throw when user misses email")
    public void validateUserMissingEmail() {
        Client newClient = new Client("", "Janusz", "Testowicz");

        IllegalArgumentException msg = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.clientService.validateClient(newClient);
        });

        Assertions.assertTrue(msg.getMessage().contains("Missing property"));
    }

    @Test
    @DisplayName("Should throw when user misses first name")
    public void validateUserMissingFirstName() {
        Client newClient = new Client("test@gmail.com", "", "Testowicz");

        IllegalArgumentException msg = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.clientService.validateClient(newClient);
        });

        Assertions.assertTrue(msg.getMessage().contains("Missing property"));
    }

    @Test
    @DisplayName("Should throw when user misses last name")
    public void validateUserMissingLastName() {
        Client newClient = new Client("test@gmail.com", "Janusz", "");

        IllegalArgumentException msg = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.clientService.validateClient(newClient);
        });

        Assertions.assertTrue(msg.getMessage().contains("Missing property"));
    }

    @Test
    @DisplayName("Should throw when user with given e-mail address already exists")
    public void addUserWithExistingEmail() {
        Client newClient = new Client("test@gmail.com", "Janusz", "Robak");
        Mockito.when(this.clientRepo.existsByEmail("test@gmail.com")).thenReturn(true);

        IllegalArgumentException msg = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.clientService.createClientAccount(newClient);
        });

        Assertions.assertTrue(msg.getMessage().contains("User with this e-mail address already exists"));
    }

    @Test
    @DisplayName("Successfully create a new user")
    public void addUser() {
        Client newClient = new Client("test@gmail.com", "Janusz", "Robak");
        Mockito.when(this.clientRepo.existsByEmail("test@gmail.com")).thenReturn(false);
        Mockito.when(this.clientRepo.save(newClient)).thenReturn(newClient);

        Assertions.assertEquals(newClient, this.clientService.createClientAccount(newClient));
    }
}

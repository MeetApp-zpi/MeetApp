package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClientAccount(Client newClient) {
        validateClient(newClient);

        if (clientRepository.existsByEmail(newClient.getEmail())) {
            throw new IllegalArgumentException("User with this e-mail address already exists");
        }

        return clientRepository.save(newClient);
    }

    // TODO: imo we should probably change all user fields to "Deleted" or sth,
    // dunno if it's required by RODO or any other law but wouldn't hurt
    public Client deleteClientAccount(Integer clientId) {
        // findById returns Optional and maybe it automatically unpacks it with .get()?
        // If the value wasn't there this line should throw exception handled in a controller
        // (I think it should work that way...)
        Client clientToDelete = clientRepository.findClientById(clientId);
        clientToDelete.setIsDeleted(true);
        return clientRepository.save(clientToDelete);
    }

    public void validateClient(Client clientObj) {
        if (clientObj.getEmail().isEmpty() || clientObj.getFirstName().isEmpty() || clientObj.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Missing property");
        }
    }
}

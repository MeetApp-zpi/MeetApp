package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    Client findClientByEmail(String email);
}

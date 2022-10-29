package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    Optional<Client> findClientByEmail(String email);
}

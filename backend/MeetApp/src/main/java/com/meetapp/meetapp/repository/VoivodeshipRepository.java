package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.City;
import com.meetapp.meetapp.model.Voivodeship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoivodeshipRepository extends JpaRepository<Voivodeship, Integer> {
    Optional<City> findByName(String name);
}

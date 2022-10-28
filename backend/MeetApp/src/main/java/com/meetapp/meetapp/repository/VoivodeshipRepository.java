package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Voivodeship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoivodeshipRepository extends JpaRepository<Voivodeship, Integer> {
}

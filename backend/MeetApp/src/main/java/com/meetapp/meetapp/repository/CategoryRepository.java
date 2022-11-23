package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByClients_Email(String email);
    List<Category> findByClients_Id(Integer id);
}

package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    // TODO: add pageable here
    List<Post> findAllByAuthorEmailIsAndIsActiveIs(String email, Boolean isActive, Pageable page);

    List<Post> findAllByEnrolleesContains(Client enrollee, Pageable page);
}

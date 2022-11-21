package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByAuthorEmailIs(String email);
}
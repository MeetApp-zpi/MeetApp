package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.PostDTO;
import com.meetapp.meetapp.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/posts/activate/{postId}")
    public PostDTO activatePost(@PathVariable Integer postId, HttpSession session) {
        return postService.activatePost(postId, session);
    }

    @GetMapping("/posts/deactivate/{postId}")
    public PostDTO deactivatePost(@PathVariable Integer postId, HttpSession session) {
        return postService.deactivatePost(postId, session);
    }
}

package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.PostDTO;
import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.*;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.NoSuchElementException;

@Service
public class PostService {
    
    private final PostRepository postRepository;
    private final AnnouncementRepository announcementRepository;
    private final MeetingRepository meetingRepository;
    private final EventRepository eventRepository;
    private final ClientRepository clientRepository;
    
    public PostService(PostRepository postRepository, AnnouncementRepository announcementRepository,
                       MeetingRepository meetingRepository, EventRepository eventRepository,
                       ClientRepository clientRepository) {
        this.postRepository = postRepository;
        this.announcementRepository = announcementRepository;
        this.meetingRepository = meetingRepository;
        this.eventRepository = eventRepository;
        this.clientRepository = clientRepository;
    }
    
    public PostDTO activatePost(Integer postId, HttpSession session) {
        Post foundPost = findPostOrThrow(postId);
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        if (foundPost.getAuthor().equals(foundClient)) {
            if (foundPost instanceof Announcement a) {
                a.setCreationDate(LocalDate.now(ZoneId.of("UTC")));
                a.setIsActive(true);
                announcementRepository.save(a);
            } else if (foundPost instanceof Meeting m) {
                if (Instant.now().isBefore(m.getMeetingDate())) {
                    m.setIsActive(true);
                    meetingRepository.save(m);
                }
            } else {
                Event casted = (Event) foundPost;
                if (Instant.now().isBefore(casted.getStartDate())) {
                    casted.setIsActive(true);
                    eventRepository.save(casted);
                }
            }
        }

        return new PostDTO(foundPost);
    }
    
    public PostDTO deactivatePost(Integer postId, HttpSession session) {
        Post foundPost = findPostOrThrow(postId);
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        if (foundPost.getAuthor().equals(foundClient)) {
            foundPost.setIsActive(false);
            foundPost.setEnrolled(0);
            foundPost.setEnrollees(new HashSet<>());
        }

        return new PostDTO(postRepository.save(foundPost));
    }
    
    public Post findPostOrThrow(Integer postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("A post with id: " + postId + " does not exist."));
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist."));
    }
}

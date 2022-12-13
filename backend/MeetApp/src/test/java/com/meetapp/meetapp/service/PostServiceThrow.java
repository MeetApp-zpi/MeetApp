package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.*;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServiceThrow {
    @Test
    public void findPost() {
        PostRepository postRepository = mock(PostRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);

        PostService postService = new PostService(postRepository, announcementRepository, meetingRepository,
                eventRepository, clientRepository);

        when(postRepository.findById(any())).thenReturn(Optional.of(new Post()));
        postService.findPostOrThrow(1);
    }

    @Test
    public void findPostThrow() {
        PostRepository postRepository = mock(PostRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);

        PostService postService = new PostService(postRepository, announcementRepository, meetingRepository,
                eventRepository, clientRepository);

        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> postService.findPostOrThrow(1));
    }

    @Test
    public void findClient() {
        PostRepository postRepository = mock(PostRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);

        PostService postService = new PostService(postRepository, announcementRepository, meetingRepository,
                eventRepository, clientRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
        postService.findClientOrThrow("jdorka");
    }

    @Test
    public void findClientThrow() {
        PostRepository postRepository = mock(PostRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);

        PostService postService = new PostService(postRepository, announcementRepository, meetingRepository,
                eventRepository, clientRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NoSuchElementException.class, () ->
                postService.findClientOrThrow("jdorka"));
    }

    @Test
    public void deactivatePost() {
        PostRepository postRepository = mock(PostRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        PostService postService = new PostService(postRepository, announcementRepository, meetingRepository,
                eventRepository, clientRepository);

        Client newClient = new Client();
        Post newPost = new Post(newClient, new Location(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(newClient));
            when(postRepository.findById(any())).thenReturn(Optional.of(newPost));
            when(postRepository.save(any())).thenReturn(newPost);
            postService.deactivatePost(1, session);
        }
    }

    @Test
    public void activatePost() {
        PostRepository postRepository = mock(PostRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        PostService postService = new PostService(postRepository, announcementRepository, meetingRepository,
                eventRepository, clientRepository);

        Client newClient = new Client();
        Announcement newAnn = new Announcement(newClient, new Location(), "Siema ogło", "giermgaioer moierg",
                new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(newClient));
            when(postRepository.findById(any())).thenReturn(Optional.of(newAnn));
            when(postRepository.save(any())).thenReturn(newAnn);
            postService.activatePost(1, session);
        }
    }

    @Test
    public void activatePostMeeting() {
        PostRepository postRepository = mock(PostRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        PostService postService = new PostService(postRepository, announcementRepository, meetingRepository,
                eventRepository, clientRepository);

        Client newClient = new Client();
        Meeting newMeeting = new Meeting(newClient, new Location(), "Siema ogło", "giermgaioer moierg",
                Instant.now(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(newClient));
            when(postRepository.findById(any())).thenReturn(Optional.of(newMeeting));
            when(postRepository.save(any())).thenReturn(newMeeting);
            postService.activatePost(1, session);
        }
    }

    @Test
    public void activatePostEvent() {
        PostRepository postRepository = mock(PostRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        HttpSession session = mock(HttpSession.class);

        PostService postService = new PostService(postRepository, announcementRepository, meetingRepository,
                eventRepository, clientRepository);

        Client newClient = new Client();
        Event newEvent = new Event(newClient, new Location(), "Siema ogło", "giermgaioer moierg",
                Instant.now(), Instant.now(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(newClient));
            when(postRepository.findById(any())).thenReturn(Optional.of(newEvent));
            when(postRepository.save(any())).thenReturn(newEvent);
            postService.activatePost(1, session);
        }
    }
}

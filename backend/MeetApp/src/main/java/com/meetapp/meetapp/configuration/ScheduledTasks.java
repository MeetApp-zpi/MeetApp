package com.meetapp.meetapp.configuration;

import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    private AnnouncementRepository announcementRepository;
    private EventRepository eventRepository;
    private MeetingRepository meetingRepository;
    private ClientRepository clientRepository;

    public ScheduledTasks(AnnouncementRepository announcementRepository, EventRepository eventRepository,
                          MeetingRepository meetingRepository, ClientRepository clientRepository) {
        this.announcementRepository = announcementRepository;
        this.eventRepository = eventRepository;
        this.meetingRepository = meetingRepository;
        this.clientRepository = clientRepository;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void checkForExpiredPosts() {
        List<Announcement> announcements = announcementRepository.findAll();
        List<Event> events = eventRepository.findAll();
        List<Meeting> meetings = meetingRepository.findAll();
        List<Client> clients = clientRepository.findAll();

        for (Announcement a : announcements) {
            Instant announcementExpirationTime = a.getCreationDate().plusMonths(1)
                    .atStartOfDay(ZoneId.of("UTC")).toInstant();
            if (Instant.now().isAfter(announcementExpirationTime)) {
                a.setIsActive(false);
                a.setEnrollees(new HashSet<>());
                a.setEnrolled(0);
            }
        }

        for (Event e : events) {
            Instant eventExpirationTime = e.getEndDate();
            if (Instant.now().isAfter(eventExpirationTime)) {
                e.setIsActive(false);
                e.setEnrollees(new HashSet<>());
                e.setEnrolled(0);
            }
        }

        for (Meeting m : meetings) {
            Instant meetingExpirationTime = m.getMeetingDate();
            if (Instant.now().isAfter(meetingExpirationTime)) {
                m.setIsActive(false);
//                m.setEnrollees(new HashSet<>());
//                m.getEnrollees().forEach(client -> {
//                    Set<Post> clientPosts = client.getPosts();
//                    boolean test = clientPosts.remove(m);
//                    System.out.println("!!!!!!! " + test);
//                    client.setPosts(clientPosts);
//                    clientRepository.save(client);
//                });
                m.setEnrolled(0);
            }
        }

        for (Client c : clients) {
            Set<Post> clientPosts = c.getPosts();
            clientPosts.removeIf(p -> !p.getIsActive());
            c.setPosts(clientPosts);
        }

        announcementRepository.saveAll(announcements);
        eventRepository.saveAll(events);
        meetingRepository.saveAll(meetings);
        clientRepository.saveAll(clients);
    }
}

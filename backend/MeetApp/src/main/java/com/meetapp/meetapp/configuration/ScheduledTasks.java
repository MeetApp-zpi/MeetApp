package com.meetapp.meetapp.configuration;

import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.repository.AnnouncementRepository;
import com.meetapp.meetapp.repository.EventRepository;
import com.meetapp.meetapp.repository.MeetingRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    private AnnouncementRepository announcementRepository;
    private EventRepository eventRepository;
    private MeetingRepository meetingRepository;

    public ScheduledTasks(AnnouncementRepository announcementRepository, EventRepository eventRepository,
                          MeetingRepository meetingRepository) {
        this.announcementRepository = announcementRepository;
        this.eventRepository = eventRepository;
        this.meetingRepository = meetingRepository;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void checkForExpiredPosts() {
        List<Announcement> announcements = announcementRepository.findAllByIsActiveIs(true);
        List<Event> events = eventRepository.findAllByIsActiveIs(true);
        List<Meeting> meetings = meetingRepository.findAllByIsActiveIs(true);

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
                m.setEnrollees(new HashSet<>());
                m.setEnrolled(0);
            }
        }

        announcementRepository.saveAll(announcements);
        eventRepository.saveAll(events);
        meetingRepository.saveAll(meetings);
    }
}

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

import java.time.ZoneId;
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
        List<Announcement> announcements = announcementRepository.findAll();
        List<Event> events = eventRepository.findAll();
        List<Meeting> meetings = meetingRepository.findAll();

        for (Announcement a : announcements) {
            long announcementExpirationTime = a.getCreationDate().plusMonths(1)
                    .atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli();
            if (System.currentTimeMillis() > announcementExpirationTime) {
                a.setIsActive(false);
            }
        }

        for (Event e : events) {
            long eventExpirationTime = e.getEndDate().toEpochMilli();
            if (System.currentTimeMillis() > eventExpirationTime) {
                e.setIsActive(false);
            }
        }

        for (Meeting m : meetings) {
            long meetingExpirationTime = m.getMeetingDate().toEpochMilli();
            if (System.currentTimeMillis() > meetingExpirationTime) {
                m.setIsActive(false);
            }
        }

        announcementRepository.saveAll(announcements);
        eventRepository.saveAll(events);
        meetingRepository.saveAll(meetings);
    }
}

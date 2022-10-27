package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.MeetingDTO;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.repository.MeetingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final LocationRepository locationRepository;

    public MeetingService(MeetingRepository meetingRepository, LocationRepository locationRepository) {
        this.meetingRepository = meetingRepository;
        this.locationRepository = locationRepository;
    }

    public List<Meeting> retrieveMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting retrieveMeeting(Integer meetingId) {
        return findMeetingOrThrow(meetingId);
    }

    public Meeting createMeeting(MeetingDTO newMeeting) {
        // TODO: CONVERT TOKEN TO AUTHOR
        Instant castedDate = parseDateOrThrow(newMeeting.getMeetingDate());
        Location foundLocation = findLocationOrThrow(newMeeting.getLocationId());
        Meeting meetingToSave;

        if (newMeeting.getPersonQuota() != null) {
            meetingToSave =
                    new Meeting(new Client(), foundLocation, newMeeting.getDescription(),
                            newMeeting.getTitle(), castedDate, newMeeting.getPersonQuota());
        } else {
            meetingToSave =
                    new Meeting(new Client(), foundLocation, newMeeting.getDescription(),
                            newMeeting.getTitle(), castedDate);
        }

        return meetingRepository.save(meetingToSave);
    }

    public Meeting updateMeeting(Integer meetingId, MeetingDTO updatedMeeting) {
        // TODO: CONVERT TOKEN TO AUTHOR
        Location foundLocation = findLocationOrThrow(updatedMeeting.getLocationId());
        Meeting foundMeeting = findMeetingOrThrow(meetingId);
        Instant parsedDate = parseDateOrThrow(updatedMeeting.getMeetingDate());

        foundMeeting.setAuthor(new Client());
        foundMeeting.setTitle(updatedMeeting.getTitle());
        foundMeeting.setMeetingDate(parsedDate);
        foundMeeting.setPersonQuota(updatedMeeting.getPersonQuota());
        foundMeeting.setDescription(updatedMeeting.getDescription());
        foundMeeting.setLocation(foundLocation);
        return meetingRepository.save(foundMeeting);
    }

    public void deleteMeeting(Integer meetingId) {
        meetingRepository.deleteById(meetingId);
    }

    public Meeting findMeetingOrThrow(Integer meetingId) {
        return meetingRepository.findById(meetingId).orElseThrow(
                () -> new NoSuchElementException("A meeting with id: " + meetingId + " does not exist."));
    }

    public Location findLocationOrThrow(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NoSuchElementException("A location with id: " + locationId + " does not exist."));
    }

    public Instant parseDateOrThrow(String dateTime) {
        try {
            return Instant.parse(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("A string '" + dateTime + "' is not in a valid time format");
        }
    }
}

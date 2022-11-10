package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.MeetingCreationDTO;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.repository.MeetingRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;

    public MeetingService(MeetingRepository meetingRepository, LocationRepository locationRepository, ClientRepository clientRepository) {
        this.meetingRepository = meetingRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
    }

    public List<Meeting> retrieveMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting retrieveMeeting(Integer meetingId) {
        return findMeetingOrThrow(meetingId);
    }

    public Meeting createMeeting(MeetingCreationDTO newMeeting, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Instant castedDate = parseDateOrThrow(newMeeting.getMeetingDate());
        Location foundLocation = findLocationOrThrow(newMeeting.getLocationId());
        Client foundClient = findClientOrThrow(email);

        Meeting meetingToSave = new Meeting(foundClient, foundLocation, newMeeting.getDescription(),
                newMeeting.getTitle(), castedDate, newMeeting.getPersonQuota());

        return meetingRepository.save(meetingToSave);
    }

    public Meeting updateMeeting(Integer meetingId, MeetingCreationDTO updatedMeeting, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(updatedMeeting.getLocationId());
        Meeting foundMeeting = findMeetingOrThrow(meetingId);
        Instant parsedDate = parseDateOrThrow(updatedMeeting.getMeetingDate());

        if (foundMeeting.getAuthor().equals(supposedAuthor)) {
            foundMeeting.setTitle(updatedMeeting.getTitle());
            foundMeeting.setMeetingDate(parsedDate);
            foundMeeting.setPersonQuota(updatedMeeting.getPersonQuota());
            foundMeeting.setDescription(updatedMeeting.getDescription());
            foundMeeting.setLocation(foundLocation);
            return meetingRepository.save(foundMeeting);
        } else {
            throw new SecurityException("Meeting with id: " + meetingId + " does not belong to the user with id: " + supposedAuthor.getId());
        }
    }

    public void deleteMeeting(Integer meetingId, HttpSession session) {
        Meeting meetingToDelete = findMeetingOrThrow(meetingId);
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);

        if (meetingToDelete.getAuthor().equals(supposedAuthor)) {
            meetingRepository.deleteById(meetingId);
        }
    }

    public Meeting findMeetingOrThrow(Integer meetingId) {
        return meetingRepository.findById(meetingId).orElseThrow(
                () -> new NoSuchElementException("A meeting with id: " + meetingId + " does not exist."));
    }

    public Location findLocationOrThrow(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NoSuchElementException("A location with id: " + locationId + " does not exist."));
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist."));
    }

    public Instant parseDateOrThrow(String dateTime) {
        try {
            return Instant.parse(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("A string '" + dateTime + "' is not in a valid time format");
        }
    }
}

package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.DateTimeDTO;
import com.meetapp.meetapp.dto.EventCreationDTO;
import com.meetapp.meetapp.dto.EventDTO;
import com.meetapp.meetapp.dto.PostDTO;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.EventRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;

    public EventService(EventRepository eventRepository, LocationRepository locationRepository,
                        ClientRepository clientRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
    }

    public List<EventDTO> retrieveEvents() {
        return eventRepository.findAll().stream()
                .map((Event event) -> new EventDTO(new PostDTO(event), event.getTitle(), event.getDescription(),
                        event.getEnrolled(), event.getPersonQuota(), event.getSchedule(),
                        new DateTimeDTO(event.getStartDate()), new DateTimeDTO(event.getEndDate()),
                        event.getPicture())).toList();
    }

    public EventDTO retrieveEvent(Integer eventId) {
        val foundEvent = findEventOrThrow(eventId);
        return new EventDTO(new PostDTO(foundEvent), foundEvent.getTitle(), foundEvent.getDescription(),
                foundEvent.getEnrolled(), foundEvent.getPersonQuota(), foundEvent.getSchedule(),
                new DateTimeDTO(foundEvent.getStartDate()), new DateTimeDTO(foundEvent.getEndDate()),
                foundEvent.getPicture());
    }

    public Event createEvent(EventCreationDTO newEvent, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client foundClient = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(newEvent.getLocationId());
        Instant startDate = parseDateOrThrow(newEvent.getStartDate());
        Instant endDate = parseDateOrThrow(newEvent.getEndDate());
        String pictureUri = newEvent.getPicture() != null ? savePictureAndGetPath(newEvent.getPicture()) : null;

        Event eventToSave =
                new Event(foundClient, foundLocation, newEvent.getTitle(), newEvent.getDescription(), startDate,
                        endDate, newEvent.getPersonQuota(), newEvent.getSchedule());
        eventToSave.setPicture(pictureUri);

        return eventRepository.save(eventToSave);
    }

    public Event updateEvent(Integer eventId, EventCreationDTO updatedEvent, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(updatedEvent.getLocationId());
        Instant startDate = parseDateOrThrow(updatedEvent.getStartDate());
        Instant endDate = parseDateOrThrow(updatedEvent.getEndDate());
        Event foundEvent = findEventOrThrow(eventId);
        String pictureUri = updatedEvent.getPicture() != null ? savePictureAndGetPath(updatedEvent.getPicture()) : null;

        if (foundEvent.getAuthor().equals(supposedAuthor)) {
            foundEvent.setDescription(updatedEvent.getDescription());
            foundEvent.setTitle(updatedEvent.getTitle());
            foundEvent.setSchedule(updatedEvent.getSchedule());
            foundEvent.setEndDate(endDate);
            foundEvent.setStartDate(startDate);
            foundEvent.setPersonQuota(updatedEvent.getPersonQuota());
            foundEvent.setLocation(foundLocation);
            foundEvent.setIsActive(true);
            foundEvent.setPicture(pictureUri);

            return eventRepository.save(foundEvent);
        } else {
            throw new SecurityException(
                    "Event with id: " + eventId + " does not belong to the user with id: " + supposedAuthor.getId());
        }
    }

    public void deleteEvent(Integer eventId, HttpSession session) {
        Event eventToDelete = findEventOrThrow(eventId);
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);

        if (eventToDelete.getAuthor().equals(supposedAuthor)) {
            eventRepository.deleteById(eventId);
        }
    }

    public String savePictureAndGetPath(MultipartFile picture) {
        try {
            LocalDateTime today = LocalDateTime.now();
            String datePath = today.getYear() + String.valueOf(today.getMonthValue());
            Path fileNameAndPath = Paths.get("static/" + datePath, picture.getOriginalFilename());
            Files.createDirectories(Paths.get("static/" + datePath));
            Files.write(fileNameAndPath, picture.getBytes());
            return fileNameAndPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Event photo with name: " + picture.getOriginalFilename() +
                    " could not be saved.");
        }
    }

    public Event findEventOrThrow(Integer eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("An event with id: " + eventId + " does not exist."));
    }

    public Location findLocationOrThrow(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NoSuchElementException("A location with id: " + locationId + " does not exist."));
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("A client with email: " + email + " does not exist."));
    }

    public Instant parseDateOrThrow(String dateString) {
        try {
            return Instant.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("A string '" + dateString + "' is not in a proper time format");
        }
    }
}

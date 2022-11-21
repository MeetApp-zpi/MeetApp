package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.*;
import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.EventRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.security.SessionManager;
import com.meetapp.meetapp.specification.EventSpecifications;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    public EventService(EventRepository eventRepository, LocationRepository locationRepository,
                        ClientRepository clientRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<EventDTO> retrieveEvents(List<Integer> categoryIds, List<Integer> locationIds,
                                         Integer sortOption, String nameSearch) {
        Specification<Event> specification = Specification.where(null);

        if (categoryIds != null) {
            specification = specification.and(EventSpecifications.hasCategory(categoryIds));
        }

        if (locationIds != null) {
            specification = specification.and(EventSpecifications.hasLocation(locationIds));
        }

        if (nameSearch != null) {
            specification = specification.and(EventSpecifications.titleContains(nameSearch));
        }

        if (sortOption != null) {
            return eventRepository.findAll(specification, paramToSortOrThrow(sortOption)).stream()
                    .map((Event event) -> new EventDTO(new PostDTO(event), event.getTitle(), event.getDescription(),
                            event.getEnrolled(), event.getPersonQuota(), event.getSchedule(),
                            new DateTimeDTO(event.getStartDate()), new DateTimeDTO(event.getEndDate()),
                            event.getPicture())).toList();
        } else {
            return eventRepository.findAll(specification).stream()
                    .map((Event event) -> new EventDTO(new PostDTO(event), event.getTitle(), event.getDescription(),
                            event.getEnrolled(), event.getPersonQuota(), event.getSchedule(),
                            new DateTimeDTO(event.getStartDate()), new DateTimeDTO(event.getEndDate()),
                            event.getPicture())).toList();
        }
    }

    public EventDTO retrieveEvent(Integer eventId) {
        val foundEvent = findEventOrThrow(eventId);
        return new EventDTO(new PostDTO(foundEvent), foundEvent.getTitle(), foundEvent.getDescription(),
                foundEvent.getEnrolled(), foundEvent.getPersonQuota(), foundEvent.getSchedule(),
                new DateTimeDTO(foundEvent.getStartDate()), new DateTimeDTO(foundEvent.getEndDate()),
                foundEvent.getPicture());
    }

    public Boolean isLoggedUserEnrolled(Integer eventId, HttpSession session) {
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        return eventRepository.existsByIdIsAndEnrolleesContains(eventId, foundClient);
    }

    public EventDTO enrollEvent(Integer eventId, HttpSession session) {
        Event foundEvent = findEventOrThrow(eventId);
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        Integer personQuota = foundEvent.getPersonQuota();
        Integer currentlyEnrolled = foundEvent.getEnrolled();

        if (!isLoggedUserEnrolled(eventId, session) && currentlyEnrolled < personQuota) {
            foundClient.getEvents().add(foundEvent);
            foundEvent.setEnrolled(foundEvent.getEnrolled() + 1);
        }

        Client savedClient = clientRepository.save(foundClient);
        Event savedEvent = eventRepository.save(foundEvent);
        return new EventDTO(new PostDTO(new Post(savedClient,
                savedEvent.getLocation(), savedEvent.getCategories())), savedEvent.getTitle(),
                savedEvent.getDescription(), savedEvent.getEnrolled(), savedEvent.getPersonQuota(),
                savedEvent.getSchedule(), new DateTimeDTO(savedEvent.getStartDate()),
                new DateTimeDTO(savedEvent.getEndDate()), savedEvent.getPicture());
    }

    public EventDTO unenrollEvent(Integer eventId, HttpSession session) {
        Event foundEvent = findEventOrThrow(eventId);
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        if (isLoggedUserEnrolled(eventId, session)) {
            foundClient.getEvents().remove(foundEvent);
            foundEvent.setEnrolled(foundEvent.getEnrolled() - 1);
        }

        Client savedClient = clientRepository.save(foundClient);
        Event savedEvent = eventRepository.save(foundEvent);
        return new EventDTO(new PostDTO(new Post(savedClient,
                savedEvent.getLocation(), savedEvent.getCategories())), savedEvent.getTitle(),
                savedEvent.getDescription(), savedEvent.getEnrolled(), savedEvent.getPersonQuota(),
                savedEvent.getSchedule(), new DateTimeDTO(savedEvent.getStartDate()),
                new DateTimeDTO(savedEvent.getEndDate()), savedEvent.getPicture());
    }

    public Event createEvent(EventCreationDTO newEvent, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client foundClient = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(newEvent.getLocationId());
        Instant startDate = parseDateOrThrow(newEvent.getStartDate());
        Instant endDate = parseDateOrThrow(newEvent.getEndDate());
        String pictureUri = newEvent.getPicture() != null ? savePictureAndGetPath(newEvent.getPicture()) : null;

        List<Category> foundCategories = findCategories(newEvent.getCategoryIds());

        Event eventToSave =
                new Event(foundClient, foundLocation, newEvent.getTitle(), newEvent.getDescription(), startDate,
                        endDate, new HashSet<>(foundCategories), newEvent.getPersonQuota(), newEvent.getSchedule());
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
            foundEvent.setCategories(new HashSet<>(findCategories(updatedEvent.getCategoryIds())));

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
            Path fileNameAndPath = Paths.get("src/main/resources/public/pictures/" + datePath, picture.getOriginalFilename());
            Files.createDirectories(Paths.get("src/main/resources/public/pictures/" + datePath));
            Files.write(fileNameAndPath, picture.getBytes());
            return String.valueOf(Paths.get(datePath, picture.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("Event photo with name: " + picture.getOriginalFilename() +
                    " could not be saved.");
        }
    }

    public List<Category> findCategories(Set<Integer> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
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

    public Sort paramToSortOrThrow(Integer sortOption) {
        return switch (sortOption) {
            case 2 -> Sort.by(Sort.Direction.ASC, "creationDate").and(Sort.by(Sort.Direction.ASC, "Id"));
            case 3 -> Sort.by(Sort.Direction.ASC, "enrolled");
            case 4 -> Sort.by(Sort.Direction.DESC, "enrolled");
            case 5 -> Sort.by(Sort.Direction.ASC, "startDate");
            default -> Sort.by(Sort.Direction.DESC, "creationDate").and(Sort.by(Sort.Direction.DESC, "Id"));
        };
    }
}

package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.EventDTO;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.EventRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventService(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    public List<Event> retrieveEvents() {
        return eventRepository.findAll();
    }

    public Event retrieveEvent(Integer eventId) {
        return findEventOrThrow(eventId);
    }

    public Event createEvent(EventDTO newEvent) {
        Location foundLocation = findLocationOrThrow(newEvent.getLocationId());
        Instant startDate = parseDateOrThrow(newEvent.getStartDate());
        Instant endDate = parseDateOrThrow(newEvent.getEndDate());

        Event eventToSave = new Event(new Client(), foundLocation, newEvent.getTitle(), newEvent.getDescription(),
                startDate, endDate, newEvent.getPersonQuota(), newEvent.getSchedule());

        return eventRepository.save(eventToSave);
    }

    public Event updateEvent(Integer eventId, EventDTO updatedEvent) {
        Location foundLocation = findLocationOrThrow(updatedEvent.getLocationId());
        Instant startDate = parseDateOrThrow(updatedEvent.getStartDate());
        Instant endDate = parseDateOrThrow(updatedEvent.getEndDate());

        Event foundEvent = findEventOrThrow(eventId);

        foundEvent.setDescription(updatedEvent.getDescription());
        foundEvent.setTitle(updatedEvent.getTitle());
        foundEvent.setSchedule(updatedEvent.getSchedule());
        foundEvent.setEndDate(endDate);
        foundEvent.setStartDate(startDate);
        foundEvent.setPersonQuota(updatedEvent.getPersonQuota());
        foundEvent.setLocation(foundLocation);
        foundEvent.setIsActive(true);

        return eventRepository.save(foundEvent);
    }

    public void deleteEvent(Integer eventId) {
        eventRepository.deleteById(eventId);
    }

    public Event findEventOrThrow(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NoSuchElementException("An event with id: " + eventId + " does not exist."));
    }

    public Location findLocationOrThrow(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NoSuchElementException("A location with id: " + locationId + " does not exist."));
    }

    public Instant parseDateOrThrow(String dateString) {
        try {
            return Instant.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("A string '" + dateString + "' is not in a proper time format");
        }
    }
}

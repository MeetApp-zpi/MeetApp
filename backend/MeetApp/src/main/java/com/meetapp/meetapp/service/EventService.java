package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.model.EventDTO;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.EventRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventService(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    public Set<Event> retrieveEvents() {
        return (Set<Event>) eventRepository.findAll();
    }

    public Event retrieveEvent(Integer eventId) {
        return eventRepository.findEventById(eventId);
    }

    public Event createEvent(EventDTO newEventDTO) {
        Location foundLoc = locationRepository.findLocationById(newEventDTO.getLocationId());
        Event newEvent = new Event();
        newEvent.setDescription(newEventDTO.getDesc());
        newEvent.setTitle(newEventDTO.getTitle());
        newEvent.setSchedule(newEventDTO.getSchedule());
        newEvent.setEndDate(newEventDTO.getEndDate());
        newEvent.setStartDate(newEventDTO.getStartDate());
        newEvent.setPersonQuota(newEventDTO.getPersonQuota());
        newEvent.setLocation(foundLoc);
        // TODO: unpack token
        newEvent.setAuthor();
        return eventRepository.save(newEvent);
    }

    public Event updateEvent(Integer eventId, EventDTO updatedEvent) {
        Location foundLoc = locationRepository.findLocationById(updatedEvent.getLocationId());
        Event existingEvent = eventRepository.findEventById(eventId);
        existingEvent.setDescription(updatedEvent.getDesc());
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setSchedule(updatedEvent.getSchedule());
        existingEvent.setEndDate(updatedEvent.getEndDate());
        existingEvent.setStartDate(updatedEvent.getStartDate());
        existingEvent.setPersonQuota(updatedEvent.getPersonQuota());
        existingEvent.setLocation(foundLoc);
        existingEvent.setIsActive(true);
        // TODO: unpack token
        existingEvent.setAuthor();
        return existingEvent;
    }

    public void deleteEvent(Integer eventId) {
        eventRepository.deleteById(eventId);
    }
}

package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.model.EventDTO;
import com.meetapp.meetapp.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArg(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/events")
    public Set<Event> getEvents(@RequestParam(required = false) List<String> category,
                                @RequestParam(required = false) String location) {
        return eventService.retrieveEvents();
    }

    @GetMapping("/events/{eventId}")
    public Event getEventInfo(@PathVariable Integer eventId) {
        return eventService.retrieveEvent(eventId);
    }

    @PostMapping("/events")
    public Event createEvent(@RequestBody EventDTO newEvent) {
        return eventService.createEvent(newEvent);
    }

    @PutMapping("/events/{eventId}")
    public Event updateMeeting(@PathVariable Integer eventId, @RequestBody EventDTO updatedEvent) {
        return eventService.updateEvent(eventId, updatedEvent);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteMeeting(@PathVariable Integer eventId) {
        eventService.deleteEvent(eventId);
    }
}

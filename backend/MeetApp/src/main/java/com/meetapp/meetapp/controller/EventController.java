package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.EventCreationDTO;
import com.meetapp.meetapp.dto.EventDTO;
import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.service.EventService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<String> handleIllegalArg(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/events")
    public List<EventDTO> getEvents(@RequestParam(required = false) List<String> categoryIds,
                                    @RequestParam(required = false) String locationId) {
        return eventService.retrieveEvents();
    }

    @GetMapping("/events/{eventId}")
    public EventDTO getEvent(@PathVariable Integer eventId) {
        return eventService.retrieveEvent(eventId);
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@Valid @RequestBody EventCreationDTO newEvent, HttpSession session) {
        return eventService.createEvent(newEvent, session);
    }

    @PutMapping("/events/{eventId}")
    public Event updateMeeting(@PathVariable Integer eventId, @Valid @RequestBody EventCreationDTO updatedEvent,
                               HttpSession session) {
        return eventService.updateEvent(eventId, updatedEvent, session);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteMeeting(@PathVariable Integer eventId, HttpSession session) {
        eventService.deleteEvent(eventId, session);
    }
}

package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.dto.EditEventDTO;
import com.meetapp.meetapp.dto.EventCreationDTO;
import com.meetapp.meetapp.dto.EventDTO;
import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.service.EventService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public List<EventDTO> getEvents(@RequestParam(required = false) List<Integer> categoryIds,
                                    @RequestParam(required = false) List<Integer> locationIds,
                                    @RequestParam(required = false) Integer sortOption,
                                    @RequestParam(required = false) String nameSearch,
                                    @RequestParam Integer page) {
        return eventService.retrieveEvents(categoryIds, locationIds, sortOption, nameSearch, true, page);
    }

    @GetMapping("/eventsInactive")
    public List<EventDTO> getInactiveEvents(@RequestParam(required = false) List<Integer> categoryIds,
                                    @RequestParam(required = false) List<Integer> locationIds,
                                    @RequestParam(required = false) Integer sortOption,
                                    @RequestParam(required = false) String nameSearch,
                                    @RequestParam Integer page) {
        return eventService.retrieveEvents(categoryIds, locationIds, sortOption, nameSearch, false, page);
    }

    @GetMapping("/events/{eventId}")
    public EventDTO getEvent(@PathVariable Integer eventId) {
        return eventService.retrieveEvent(eventId);
    }

    @GetMapping("/events/editDetails/{eventId}")
    public EditEventDTO getEditDetails(@PathVariable Integer eventId) { return eventService.retrieveEditDetails(eventId); }

    @GetMapping("/events/isEnrolled/{eventId}")
    public Boolean isLoggedUserEnrolled(@PathVariable Integer eventId, HttpSession session) {
        return eventService.isLoggedUserEnrolled(eventId, session);
    }

    @GetMapping("/events/enroll/{eventId}")
    public EventDTO enrollEvent(@PathVariable Integer eventId, HttpSession session) {
        return eventService.enrollEvent(eventId, session);
    }

    @GetMapping("/events/unenroll/{eventId}")
    public EventDTO unenrollEvent(@PathVariable Integer eventId, HttpSession session) {
        return eventService.unenrollEvent(eventId, session);
    }

    @PostMapping(value = "/events", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@Valid @ModelAttribute EventCreationDTO newEvent, HttpSession session) {
        return eventService.createEvent(newEvent, session);
    }

    @PutMapping(value = "/events/{eventId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public Event updateMeeting(@PathVariable Integer eventId, @Valid @ModelAttribute EventCreationDTO updatedEvent,
                               HttpSession session) {
        return eventService.updateEvent(eventId, updatedEvent, session);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteMeeting(@PathVariable Integer eventId, HttpSession session) {
        eventService.deleteEvent(eventId, session);
    }
}

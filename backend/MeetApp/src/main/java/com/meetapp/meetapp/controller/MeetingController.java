package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.MeetingCreationDTO;
import com.meetapp.meetapp.dto.MeetingDTO;
import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.service.MeetingService;
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
public class MeetingController {
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class, DataIntegrityViolationException.class})
    public ResponseEntity<String> handleIllegalArg(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/meetings")
    public List<MeetingDTO> getMeetings(@RequestParam(required = false) List<String> categoryIds,
                                        @RequestParam(required = false) String locationId) {
        return meetingService.retrieveMeetings();
    }

    @GetMapping("/meetings/{meetingId}")
    public MeetingDTO getMeeting(@PathVariable Integer meetingId) {
        return meetingService.retrieveMeeting(meetingId);
    }

    @PostMapping("/meetings")
    @ResponseStatus(HttpStatus.CREATED)
    public Meeting createMeeting(@Valid @RequestBody MeetingCreationDTO newMeeting, HttpSession session) {
        return meetingService.createMeeting(newMeeting, session);
    }

    @PutMapping("/meetings/{meetingId}")
    public Meeting updateMeeting(@PathVariable Integer meetingId, @Valid @RequestBody MeetingCreationDTO updatedMeeting,
                                 HttpSession session) {
        return meetingService.updateMeeting(meetingId, updatedMeeting, session);
    }

    @DeleteMapping("/meetings/{meetingId}")
    public void deleteMeeting(@PathVariable Integer meetingId, HttpSession session) {
        meetingService.deleteMeeting(meetingId, session);
    }
}

package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.MeetingDTO;
import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.service.MeetingService;
import jakarta.validation.Valid;
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

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleIllegalArg(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/meetings")
    public List<Meeting> getMeetings(@RequestParam(required = false) List<String> categoryIds,
                                     @RequestParam(required = false) String locationId) {
        return meetingService.retrieveMeetings();
    }

    @GetMapping("/meetings/{meetingId}")
    public Meeting getMeetingInfo(@PathVariable Integer meetingId) {
        return meetingService.retrieveMeeting(meetingId);
    }

    @PostMapping("/meetings")
    @ResponseStatus(HttpStatus.CREATED)
    public Meeting createMeeting(@Valid @RequestBody MeetingDTO newMeeting) {
        return meetingService.createMeeting(newMeeting);
    }

    @PutMapping("/meetings/{meetingId}")
    public Meeting updateMeeting(@PathVariable Integer meetingId, @Valid @RequestBody MeetingDTO updatedMeeting) {
        return meetingService.updateMeeting(meetingId, updatedMeeting);
    }

    @DeleteMapping("/meetings/{meetingId}")
    public void deleteMeeting(@PathVariable Integer meetingId) {
        meetingService.deleteMeeting(meetingId);
    }
}

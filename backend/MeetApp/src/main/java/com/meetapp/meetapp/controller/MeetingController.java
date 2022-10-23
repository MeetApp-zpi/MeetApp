package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.model.MeetingDTO;
import com.meetapp.meetapp.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArg(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/meetings")
    public Set<Meeting> getMeetings(@RequestParam(required = false) List<String> category,
                                    @RequestParam(required = false) String location) {
        return meetingService.retrieveMeetings();
    }

    @GetMapping("/meetings/{meetingId}")
    public Meeting getMeetingInfo(@PathVariable Integer meetingId) {
        return meetingService.retrieveMeeting(meetingId);
    }

    @PostMapping("/meetings")
    public Meeting createMeeting(@RequestBody MeetingDTO newMeeting) {
        return meetingService.createMeeting(newMeeting);
    }

    @PutMapping("/meetings/{meetingId}")
    public Meeting updateMeeting(@PathVariable Integer meetingId, @RequestBody MeetingDTO updatedMeeting) {
        return meetingService.updateMeeting(meetingId, updatedMeeting);
    }

    @DeleteMapping("/meetings/{meetingId}")
    public void deleteMeeting(@PathVariable Integer meetingId) {
        meetingService.deleteMeeting(meetingId);
    }
}

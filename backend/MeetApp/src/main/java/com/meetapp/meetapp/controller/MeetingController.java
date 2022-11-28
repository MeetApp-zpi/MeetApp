package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.AnnouncementDTO;
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
    public List<MeetingDTO> getMeetings(@RequestParam(required = false) List<Integer> categoryIds,
                                        @RequestParam(required = false) List<Integer> locationIds,
                                        @RequestParam(required = false) Integer sortOption,
                                        @RequestParam(required = false) String nameSearch,
                                        @RequestParam Integer page) {
        return meetingService.retrieveMeetings(categoryIds, locationIds, sortOption, nameSearch, page);
    }

    @GetMapping("/meetings/{meetingId}")
    public MeetingDTO getMeeting(@PathVariable Integer meetingId) {
        return meetingService.retrieveMeeting(meetingId);
    }

    @GetMapping("/meetings/isEnrolled/{meetingId}")
    public Boolean isLoggedUserEnrolled(@PathVariable Integer meetingId, HttpSession session) {
        return meetingService.isLoggedUserEnrolled(meetingId, session);
    }

    @GetMapping("/meetings/enroll/{meetingId}")
    public MeetingDTO enrollMeeting(@PathVariable Integer meetingId, HttpSession session) {
        return meetingService.enrollMeeting(meetingId, session);
    }

    @GetMapping("/meetings/unenroll/{meetingId}")
    public MeetingDTO unenrollMeeting(@PathVariable Integer meetingId, HttpSession session) {
        return meetingService.unenrollMeeting(meetingId, session);
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

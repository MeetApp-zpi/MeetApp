package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.service.AnnouncementService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleIllegalArg(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/announcements")
    public List<Announcement> getAnnouncements(@RequestParam(required = false) List<Integer> categoryIds,
                                               @RequestParam(required = false) Integer locationId) {
        return announcementService.retrieveAnnouncements();
    }

    @GetMapping("/announcements/{announcementId}")
    public Announcement getAnnouncementInfo(@PathVariable Integer announcementId) {
        return announcementService.retrieveAnnouncement(announcementId);
    }

    @PostMapping("/announcements")
    @ResponseStatus(HttpStatus.CREATED)
    public Announcement createAnnouncement(@Valid @RequestBody AnnouncementDTO newAnnouncement,
                                           HttpSession session) {
        return announcementService.createAnnouncement(newAnnouncement, session);
    }

    @PutMapping("/announcements/{announcementId}")
    public Announcement updateAnnouncement(@PathVariable Integer announcementId,
                                           @Valid @RequestBody AnnouncementDTO updatedAnnouncement,
                                           HttpSession session) {
        return announcementService.updateAnnouncement(announcementId, updatedAnnouncement, session);
    }

    @DeleteMapping("/announcements/{announcementId}")
    public void deleteAnnouncement(@PathVariable Integer announcementId, HttpSession session) {
        announcementService.deleteAnnouncement(announcementId, session);
    }
}

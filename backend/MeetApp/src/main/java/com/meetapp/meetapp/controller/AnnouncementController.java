package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.service.AnnouncementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleIllegalArg(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/announcements")
    public Set<Announcement> getAnnouncements(@RequestParam(required = false) List<Integer> categoryIds,
                                              @RequestParam(required = false) Integer locationId) {
        return announcementService.retrieveAnnouncements();
    }

    @GetMapping("/announcements/{announcementId}")
    public Announcement getAnnouncementInfo(@PathVariable Integer announcementId) {
        return announcementService.retrieveAnnouncement(announcementId);
    }

    @PostMapping("/announcements")
    public Announcement createAnnouncement(@Valid @RequestBody AnnouncementDTO newAnnouncement) {
        return announcementService.createAnnouncement(newAnnouncement);
    }

    @PutMapping("/announcements/{announcementId}")
    public Announcement updateAnnouncement(@PathVariable Integer announcementId, @Valid @RequestBody AnnouncementDTO updatedAnnouncement) {
        return announcementService.updateAnnouncement(announcementId, updatedAnnouncement);
    }

    @DeleteMapping("/announcements/{announcementId}")
    public void deleteAnnouncement(@PathVariable Integer announcementId) {
        announcementService.deleteAnnouncement(announcementId);
    }
}

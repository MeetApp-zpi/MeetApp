package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.security.SessionManager;
import com.meetapp.meetapp.service.AnnouncementService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.http.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/testSession")
    public boolean returnSess(HttpSession session) {
        return SessionManager.isAuthenticated(session);
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
                                           OAuth2AuthenticationToken authToken, HttpSession sess) throws AuthenticationException {
        return announcementService.createAnnouncement(newAnnouncement, sess);
    }

    @PutMapping("/announcements/{announcementId}")
    public Announcement updateAnnouncement(@PathVariable Integer announcementId,
                                           @Valid @RequestBody AnnouncementDTO updatedAnnouncement) {
        return announcementService.updateAnnouncement(announcementId, updatedAnnouncement);
    }

    @DeleteMapping("/announcements/{announcementId}")
    public void deleteAnnouncement(@PathVariable Integer announcementId) {
        announcementService.deleteAnnouncement(announcementId);
    }
}

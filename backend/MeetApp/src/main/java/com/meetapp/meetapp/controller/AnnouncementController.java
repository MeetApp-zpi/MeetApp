package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.dto.AnnouncementCreationDTO;
import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.dto.SingleAnnouncementDTO;
import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.service.AnnouncementService;
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
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class, DataIntegrityViolationException.class})
    public ResponseEntity<String> handleIllegalArg(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/announcements")
    public List<AnnouncementDTO> getAnnouncements(@RequestParam(required = false) List<Integer> categoryIds,
                                                  @RequestParam(required = false) List<Integer> locationIds,
                                                  @RequestParam(required = false) Integer sortOption,
                                                  @RequestParam(required = false) String nameSearch,
                                                  @RequestParam Integer page) {
        return announcementService.retrieveAnnouncements(categoryIds, locationIds, sortOption, nameSearch, page);
    }

    @GetMapping("/announcements/{announcementId}")
    public SingleAnnouncementDTO getAnnouncement(@PathVariable Integer announcementId) {
        return announcementService.retrieveAnnouncement(announcementId);
    }

    @GetMapping("/announcements/isEnrolled/{announcementId}")
    public Boolean isLoggedUserEnrolled(@PathVariable Integer announcementId, HttpSession session) {
        return announcementService.isLoggedUserEnrolled(announcementId, session);
    }

    @GetMapping("/announcements/enroll/{announcementId}")
    public AnnouncementDTO enrollAnnouncement(@PathVariable Integer announcementId, HttpSession session) {
        return announcementService.enrollAnnouncement(announcementId, session);
    }

    @GetMapping("/announcements/unenroll/{announcementId}")
    public AnnouncementDTO unenrollAnnouncement(@PathVariable Integer announcementId, HttpSession session) {
        return announcementService.unenrollAnnouncement(announcementId, session);
    }

    @GetMapping("/announcements/deactivate/{announcementId}")
    public AnnouncementDTO deactivateAnnouncement(@PathVariable Integer announcementId, HttpSession session) {
        return announcementService.deactivateAnnouncement(announcementId, session);
    }

    @GetMapping("/announcements/activate/{announcementId}")
    public AnnouncementDTO activateAnnouncement(@PathVariable Integer announcementId, HttpSession session) {
        return announcementService.activateAnnouncement(announcementId, session);
    }

    @PostMapping("/announcements")
    @ResponseStatus(HttpStatus.CREATED)
    public Announcement createAnnouncement(@Valid @RequestBody AnnouncementCreationDTO newAnnouncement,
                                           HttpSession session) {
        return announcementService.createAnnouncement(newAnnouncement, session);
    }

    @PutMapping("/announcements/{announcementId}")
    public Announcement updateAnnouncement(@PathVariable Integer announcementId,
                                           @Valid @RequestBody AnnouncementCreationDTO updatedAnnouncement,
                                           HttpSession session) {
        return announcementService.updateAnnouncement(announcementId, updatedAnnouncement, session);
    }

    @DeleteMapping("/announcements/{announcementId}")
    public void deleteAnnouncement(@PathVariable Integer announcementId, HttpSession session) {
        announcementService.deleteAnnouncement(announcementId, session);
    }
}

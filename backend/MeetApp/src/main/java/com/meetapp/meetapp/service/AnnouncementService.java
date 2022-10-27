package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.AnnouncementRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Service;

import lombok.val;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, LocationRepository locationRepository,
                               ClientRepository clientRepository) {
        this.announcementRepository = announcementRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
    }

    public List<Announcement> retrieveAnnouncements() {
        return announcementRepository.findAll();
    }

    public Announcement retrieveAnnouncement(Integer announcementId) {
        return findAnnouncementOrThrow(announcementId);
    }

    public Announcement createAnnouncement(AnnouncementDTO newAnnouncement, HttpSession sess) throws AuthenticationException {
        String email = SessionManager.retrieveEmailOrThrow(sess);
        val foundClient = findClientOrThrow(email);
        val foundLocation = findLocationOrThrow(newAnnouncement.getLocationId());

        Announcement announcementToSave =
                new Announcement(foundClient, foundLocation, newAnnouncement.getDescription(),
                        newAnnouncement.getTitle());

        return announcementRepository.save(announcementToSave);
    }

    public Announcement updateAnnouncement(Integer announcementId, AnnouncementDTO updatedAnnouncement) {
        Location foundLocation = findLocationOrThrow(updatedAnnouncement.getLocationId());
        Announcement foundAnnouncement = findAnnouncementOrThrow(announcementId);

        foundAnnouncement.setLocation(foundLocation);
        foundAnnouncement.setTitle(updatedAnnouncement.getTitle());
        foundAnnouncement.setDescription(updatedAnnouncement.getDescription());
        return announcementRepository.save(foundAnnouncement);
    }

    public void deleteAnnouncement(Integer announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    Announcement findAnnouncementOrThrow(Integer announcementId) {
        return announcementRepository.findById(announcementId).orElseThrow(
                () -> new NoSuchElementException("An announcement with id: " + announcementId + " does not exist."));
    }

    Location findLocationOrThrow(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NoSuchElementException("A location with id: " + locationId + " does not exist."));
    }

    Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist."));
    }
}

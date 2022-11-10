package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.AnnouncementCreationDTO;
import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.dto.PostDTO;
import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.model.Post;
import com.meetapp.meetapp.repository.AnnouncementRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.stereotype.Service;

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

    public List<AnnouncementDTO> retrieveAnnouncements() {
        return announcementRepository.findAll().stream()
                .map((Announcement announcement) -> new AnnouncementDTO(new PostDTO(announcement),
                        announcement.getTitle(), announcement.getDescription(),
                        announcement.getEnrolled())).toList();
    }

    public AnnouncementDTO retrieveAnnouncement(Integer announcementId) {
        val foundAnnouncement = findAnnouncementOrThrow(announcementId);
        return new AnnouncementDTO(new PostDTO(foundAnnouncement), foundAnnouncement.getTitle(),
                foundAnnouncement.getDescription(), foundAnnouncement.getEnrolled());
    }

    public Announcement createAnnouncement(AnnouncementCreationDTO newAnnouncement, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client foundClient = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(newAnnouncement.getLocationId());

        Announcement announcementToSave =
                new Announcement(foundClient, foundLocation, newAnnouncement.getDescription(),
                        newAnnouncement.getTitle());

        return announcementRepository.save(announcementToSave);
    }

    public Announcement updateAnnouncement(Integer announcementId, AnnouncementCreationDTO updatedAnnouncement,
                                           HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(updatedAnnouncement.getLocationId());
        Announcement foundAnnouncement = findAnnouncementOrThrow(announcementId);

        if (foundAnnouncement.getAuthor().equals(supposedAuthor)) {
            foundAnnouncement.setLocation(foundLocation);
            foundAnnouncement.setTitle(updatedAnnouncement.getTitle());
            foundAnnouncement.setDescription(updatedAnnouncement.getDescription());
            return announcementRepository.save(foundAnnouncement);
        } else {
            throw new SecurityException(
                    "Announcement with id: " + announcementId + " does not belong to the user with id: " +
                            supposedAuthor.getId());
        }
    }

    public void deleteAnnouncement(Integer announcementId, HttpSession session) {
        Announcement announcementToDelete = findAnnouncementOrThrow(announcementId);
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);

        if (announcementToDelete.getAuthor().equals(supposedAuthor)) {
            announcementRepository.deleteById(announcementId);
        }
    }

    public Announcement findAnnouncementOrThrow(Integer announcementId) {
        return announcementRepository.findById(announcementId).orElseThrow(
                () -> new NoSuchElementException("An announcement with id: " + announcementId + " does not exist."));
    }

    public Location findLocationOrThrow(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NoSuchElementException("A location with id: " + locationId + " does not exist."));
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist."));
    }
}
